package de.fhswf.genericapplication.configs;

import de.fhswf.genericapplication.models.BaseEntity;
import de.fhswf.genericapplication.services.BaseEntityService;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Config to provice type registry.
 *
 * @author Kevin Link
 */
@Configuration
public class TypeConfig {

    private final static Logger LOG = LoggerFactory.getLogger(TypeConfig.class);

    @Value("${de.fhswf.genericapplication.entity.scan.package}")
    private String entityPackage;

    /**
     * Registry to return a map of types by their hash code.
     *
     * @return Map of registered types.
     */
    @Bean
    public Map<Long, Class<? extends BaseEntity>> typeRegistry() {
        final Map<Long, Class<? extends BaseEntity>> types = new HashMap<>();

        LOG.info("Search for entities in " + entityPackage + " package...");

        Reflections reflections = new Reflections(entityPackage);

        reflections.getSubTypesOf(BaseEntity.class)
                .forEach(aClass -> {
                    long typeId = BaseEntityService.getTypeIdFromBaseEntity(aClass);
                    LOG.info("Registered entity: " + aClass.getName() + " with typeId: " + typeId);
                    types.put(typeId, aClass);
                });

        LOG.info("Registered " + types.size() + " entities in total.");

        return types;
    }

}
