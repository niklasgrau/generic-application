package de.fhswf.genericapplication.converters.impl;

import de.fhswf.genericapplication.converters.GenericEntityConverter;
import de.fhswf.genericapplication.dto.GenericEntityDto;
import de.fhswf.genericapplication.exceptions.DateParseException;
import de.fhswf.genericapplication.exceptions.PropertyNotFoundException;
import de.fhswf.genericapplication.models.BaseEntity;
import de.fhswf.genericapplication.models.UploadFile;
import de.fhswf.genericapplication.repositories.GenericEntityRepository;
import de.fhswf.genericapplication.services.BaseEntityService;
import de.fhswf.genericapplication.utils.GenericEntityClassUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Default implementation of the conversion interface {@link GenericEntityConverter}.
 *
 * @author Niklas Grau
 */
@Component
public class DefaultGenericEntityConverter implements GenericEntityConverter {
    @Autowired
    GenericEntityClassUtils genericEntityClassUtils;

    @Autowired
    BaseEntityService baseEntityService;

    @Autowired
    GenericEntityRepository genericEntityRepository;

    @Autowired
    PropertyConverterRegistry propertyConverterRegistry;

    List<String> ignoredPropertiesList = List.of("typeId");

    @Override
    public GenericEntityDto convert(BaseEntity source) {
        Map<String, Object> properties = this.propertyConverterRegistry.convert(source);

        // Find associations and add them to the property bag
        Map<String, Object> associationProperties = this.genericEntityClassUtils.entityAssociationAttributesAsMap(source);
        associationProperties.forEach((key, value) -> {
            // Property bag must only contain simple properties and to-one associations, to-many will be ignored.
            if (value instanceof BaseEntity associationEntity) {
                BaseEntity nonProxyEntity = (BaseEntity) Hibernate.unproxy(associationEntity);
                Long typeId = BaseEntityService.getTypeIdFromBaseEntity(nonProxyEntity.getClass());

                properties.put(key, new GenericEntityDto(typeId, associationEntity.getId(), null));
            }
        });

        return new GenericEntityDto(BaseEntityService.getTypeIdFromBaseEntity(source.getClass()), source.getId(), properties);
    }

    /**
     * Converts the given properties to a {@link BaseEntity}.
     *
     * @param beanWrapper the targets entity class wrapper to set the source properties.
     * @param properties  the targets entity class attributes to set. Must only contain simple properties and to-one associations.
     * @return the {@link BaseEntity}
     */
    public BaseEntity convert(BeanWrapper beanWrapper, Map<String, ?> properties) {
        properties.forEach((key, value) -> {
            if (ignoredPropertiesList.contains(key)) {
                return;
            }

            if (value != null) {
                // Find and set to-one associations
                if (value instanceof Map association) {
                    Long typeId = Optional.ofNullable(association.get("typeId")).map(Number.class::cast).map(Number::longValue).orElse(null);
                    Long id = Optional.ofNullable(association.get("id")).map(Number.class::cast).map(Number::longValue).orElse(null);

                    try {
                        Class<? extends BaseEntity> associationClass = this.baseEntityService.getClassByTypeId(typeId);
                        BaseEntity associationEntity = this.genericEntityRepository.findById(associationClass, id);
                        beanWrapper.setPropertyValue(key, associationEntity);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                    return;
                }

                Class<?> propertyType = beanWrapper.getPropertyType(key);
                if (propertyType == null) {
                    throw new PropertyNotFoundException(key);
                }

                // Ignore upload files
                if (propertyType == UploadFile.class) {
                    return;
                }

                // BeanWrapper is not able to handle properties of type Date.
                // The value has to be converted to the target type beforehand.
                if (propertyType == Date.class) {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");

                    try {
                        Date date = dateFormat.parse((String) value);
                        beanWrapper.setPropertyValue(key, date);
                    } catch (ParseException e) {
                        throw new DateParseException((String) value, e);
                    }

                    return;
                }
            }

            beanWrapper.setPropertyValue(key, value);
        });

        return (BaseEntity) beanWrapper.getWrappedInstance();
    }

    @Override
    public BaseEntity convert(GenericEntityDto source, BaseEntity target) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(target);
        return this.convert(beanWrapper, source.getProperties());
    }

    @Override
    public BaseEntity convert(GenericEntityDto source, Class<? extends BaseEntity> target) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(target);
        return this.convert(beanWrapper, source.getProperties());
    }
}
