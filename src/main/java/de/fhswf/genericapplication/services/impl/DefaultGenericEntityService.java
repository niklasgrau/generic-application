package de.fhswf.genericapplication.services.impl;

import de.fhswf.genericapplication.converters.impl.DefaultGenericEntityConverter;
import de.fhswf.genericapplication.dto.AssociationStateDto;
import de.fhswf.genericapplication.dto.GenericEntityDto;
import de.fhswf.genericapplication.dto.requests.FilterStatement;
import de.fhswf.genericapplication.exceptions.ModelConstraintException;
import de.fhswf.genericapplication.exceptions.ModelNotFoundException;
import de.fhswf.genericapplication.exceptions.PropertyNotFoundException;
import de.fhswf.genericapplication.models.BaseEntity;
import de.fhswf.genericapplication.repositories.GenericEntityRepository;
import de.fhswf.genericapplication.services.AssociationStateService;
import de.fhswf.genericapplication.services.GenericEntityService;
import de.fhswf.genericapplication.utils.GenericEntityClassUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Default implementation of the {@link GenericEntityService}. The service handles CRUD operations
 * on entities by the conversion between {@link BaseEntity} and {@link GenericEntityDto},
 * managing their association states and persisting of the given data in the database.
 *
 * @author Niklas Grau
 */
@Service
public class DefaultGenericEntityService implements GenericEntityService {
    @Autowired
    GenericEntityRepository entityRepository;

    @Autowired
    GenericEntityClassUtils entityClassUtils;

    @Autowired
    DefaultGenericEntityConverter entityConverter;

    @Autowired
    DefaultBaseEntityService baseEntityService;

    @Autowired
    AssociationStateService associationStateService;

    @Override
    public GenericEntityDto create(
            GenericEntityDto entityDto, Map<String, AssociationStateDto> associationStateDtos
    ) {
        Class<? extends BaseEntity> entityClass;
        try {
            entityClass = this.baseEntityService.getClassByTypeId(entityDto.getTypeId());
        } catch (ClassNotFoundException e) {
            throw new ModelNotFoundException(entityDto.getTypeId(), entityDto.getId(), e);
        }

        BaseEntity entityToCreate = entityConverter.convert(entityDto, entityClass);
        BaseEntity entity = this.entityRepository.create(entityToCreate);
        this.processAssociationStateChanges(entity, associationStateDtos);

        BaseEntity resultEntity = this.entityRepository.update(entity);
        return this.entityConverter.convert(resultEntity);
    }

    @Override
    public GenericEntityDto get(Long typeId, Long id, List<String> propertySelection) {
        BaseEntity entity;
        try {
            Class<? extends BaseEntity> entityClass = this.baseEntityService.getClassByTypeId(typeId);
            entity = this.entityRepository.findById(entityClass, id);
        } catch (ClassNotFoundException e) {
            throw new ModelNotFoundException(typeId, id, e);
        }

        if (entity == null) {
            throw new ModelNotFoundException(typeId, id);
        }

        GenericEntityDto resultDto = this.entityConverter.convert(entity);
        if (!propertySelection.isEmpty()) {
            filterProperties(propertySelection, resultDto);
        }

        return resultDto;
    }

    @Override
    public Page<GenericEntityDto> list(
            Long typeId, List<String> propertySelection, Pageable pagination, FilterStatement filter
    ) {
        Class<? extends BaseEntity> entityClass;
        try {
            entityClass = this.baseEntityService.getClassByTypeId(typeId);
        } catch (ClassNotFoundException e) {
            throw new ModelNotFoundException(typeId, e);
        }

        Page<? extends BaseEntity> entities = this.entityRepository.findAll(entityClass, pagination, filter);
        List<GenericEntityDto> resultDtoList = new ArrayList<>();
        for (BaseEntity entity : entities) {
            GenericEntityDto resultDto = this.entityConverter.convert(entity);
            if (propertySelection != null && !propertySelection.isEmpty()) {
                filterProperties(propertySelection, resultDto);
            }
            resultDtoList.add(resultDto);
        }

        return new PageImpl<>(resultDtoList, entities.getPageable(), entities.getTotalElements());
    }

    @Override
    public GenericEntityDto update(
            Long id, GenericEntityDto entityDto, Map<String, AssociationStateDto> associationStateDtos
    ) {
        Class<? extends BaseEntity> entityClass;
        try {
            entityClass = this.baseEntityService.getClassByTypeId(entityDto.getTypeId());
        } catch (ClassNotFoundException e) {
            throw new ModelNotFoundException(entityDto.getTypeId(), entityDto.getId(), e);
        }

        BaseEntity entityToUpdate = this.entityRepository.findById(entityClass, id);
        BaseEntity entity = entityConverter.convert(entityDto, entityToUpdate);
        this.processAssociationStateChanges(entity, associationStateDtos);

        BaseEntity resultEntity = this.entityRepository.update(entity);
        return this.entityConverter.convert(resultEntity);
    }

    @Override
    public void delete(Long typeId, Long id) {
        try {
            Class<? extends BaseEntity> entityClass = this.baseEntityService.getClassByTypeId(typeId);
            BaseEntity entity = this.entityRepository.findById(entityClass, id);
            this.entityRepository.delete(entity);
        } catch (ClassNotFoundException e) {
            throw new ModelNotFoundException(typeId, id, e);
        } catch (DataIntegrityViolationException e) {
            String constraintName = ((ConstraintViolationException) e.getCause()).getConstraintName();
            Pattern pattern = Pattern.compile("PUBLIC\\.(.*) FOREIGN\\.*", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(constraintName);

            if (matcher.find()) {
                throw new ModelConstraintException(this.baseEntityService.getBaseEntity(typeId, id), matcher.group(1), e);
            }

            throw e;
        }
    }

    /**
     * Updates the associations according to the given association state changes.
     *
     * @param entity            the {@link BaseEntity} which associations has to be changed.
     * @param associationStates the Map of {@link AssociationStateDto} containing the changes.
     */
    private void processAssociationStateChanges(BaseEntity entity, Map<String, AssociationStateDto> associationStates) {
        if (associationStates == null) {
            return;
        }

        associationStates.forEach((associationName, associationStateDto) -> {
            if (!this.entityClassUtils.isProperty(entity, associationName)) {
                throw new PropertyNotFoundException(associationName);
            }

            // Connect associations to origin entity
            associationStateDto.getConnected().forEach(genericEntityDto -> {
                this.associationStateService.connect(
                        genericEntityDto.getTypeId(), genericEntityDto.getId(), associationName, entity
                );
            });

            // Disconnect associated entities from origin entity
            associationStateDto.getDisconnected().forEach(genericEntityDto -> {
                this.associationStateService.disconnect(
                        genericEntityDto.getTypeId(), genericEntityDto.getId(), associationName, entity
                );
            });

            // Delete associated entities
            associationStateDto.getDeleted().forEach(genericEntityDto -> {
                this.associationStateService.delete(genericEntityDto.getTypeId(), genericEntityDto.getId());
            });
        });
    }

    /**
     * Retains all properties from the {@link GenericEntityDto}.
     *
     * @param propertySelection names of the properties to keep.
     * @param resultDto         entity to retain properties from.
     */
    private static void filterProperties(List<String> propertySelection, GenericEntityDto resultDto) {
        resultDto.getProperties().keySet().retainAll(propertySelection);
    }
}
