package de.fhswf.genericapplication.configs;

import de.fhswf.genericapplication.converters.impl.PropertyConverterRegistry;
import de.fhswf.genericapplication.converters.impl.UploadFilePropertyConverter;
import de.fhswf.genericapplication.dto.GenericEntityDto;
import de.fhswf.genericapplication.models.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Config to provide property converters.
 *
 * @author Kevin Link
 */
@Configuration
public class PropertyConverterConfig {

    @Autowired
    private UploadFilePropertyConverter uploadFilePropertyConverter;

    /**
     * Registry to hold property converters used by convertion from {@link BaseEntity} to {@link GenericEntityDto}.
     *
     * @return Returns the registry instance
     */
    @Bean
    public PropertyConverterRegistry propertyConverterRegistry() {
        PropertyConverterRegistry registry = PropertyConverterRegistry.getInstance();

        registry.register(uploadFilePropertyConverter);

        return registry;
    }

}
