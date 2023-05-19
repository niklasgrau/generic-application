package de.fhswf.genericapplication.converters.impl;

import de.fhswf.genericapplication.converters.PropertyConverter;
import de.fhswf.genericapplication.models.BaseEntity;
import de.fhswf.genericapplication.utils.GenericEntityClassUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Registry to collect a map of {@link PropertyConverter} implementations to provide while converting
 * {@link BaseEntity}'s to {@link de.fhswf.genericapplication.dto.GenericEntityDto}'s.
 *
 * @author Kevin Link
 */
public final class PropertyConverterRegistry {

    @Autowired
    private GenericEntityClassUtils genericEntityClassUtils;

    private static final PropertyConverterRegistry instance = new PropertyConverterRegistry();
    private final Map<Class<?>, PropertyConverter<?, ?>> converterMap;

    private PropertyConverterRegistry() {
        this.converterMap = new ConcurrentHashMap<>();
    }

    public void register(PropertyConverter<?, ?> converter) {
        converterMap.put(converter.supports(), converter);
    }

    public PropertyConverter<?, ?> get(Class<?> clazz) {
        return converterMap.get(clazz);
    }

    public static PropertyConverterRegistry getInstance() {
        return instance;
    }

    public Map<String, Object> convert(BaseEntity entity) {
        Map<String, Object> properties = genericEntityClassUtils.entityAttributesAsMap(entity);

        for (Map.Entry<String, Object> entry : properties.entrySet()) {
            Class<?> valueClass = entry.getValue().getClass();

            if (converterMap.containsKey(valueClass)) {
                PropertyConverter propertyConverter = converterMap.get(valueClass);
                entry.setValue(propertyConverter.convert(entry.getValue(), entry.getKey(), entity));
            }

        }
        return properties;
    }
}
