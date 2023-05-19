package de.fhswf.genericapplication.services.impl;

import de.fhswf.genericapplication.exceptions.ModelNotFoundException;
import de.fhswf.genericapplication.models.BaseEntity;
import de.fhswf.genericapplication.repositories.GenericEntityRepository;
import de.fhswf.genericapplication.services.BaseEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Default implementation of {@link BaseEntityService} to receive {@link BaseEntity}'s.
 *
 * @author Kevin Link
 */
@Service
public class DefaultBaseEntityService implements BaseEntityService {

    @Autowired
    private GenericEntityRepository genericEntityRepository;

    @Autowired
    private Map<Long, Class<? extends BaseEntity>> typeRegistry;

    @Override
    public BaseEntity getBaseEntity(Long typeId, Long id) throws ModelNotFoundException {
        try {
            Class<? extends BaseEntity> entityClass = this.getClassByTypeId(typeId);
            return genericEntityRepository.findById(entityClass, id);
        } catch (ClassNotFoundException e) {
            throw new ModelNotFoundException(typeId, id, e);
        }
    }

    @Override
    public Class<? extends BaseEntity> getClassByTypeId(Long typeId) throws ClassNotFoundException, NumberFormatException {
        Class<? extends BaseEntity> aClass = typeRegistry.get(typeId);
        if (aClass == null) throw new ClassNotFoundException("Can't find any class by type id " + typeId);
        return aClass;
    }
}
