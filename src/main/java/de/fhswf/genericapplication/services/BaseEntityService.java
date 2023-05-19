package de.fhswf.genericapplication.services;

import de.fhswf.genericapplication.exceptions.ModelNotFoundException;
import de.fhswf.genericapplication.models.BaseEntity;

/**
 * Service for convenience methods on {@link BaseEntity}'s
 *
 * @author Link, Kevin
 */
public interface BaseEntityService {

    /**
     * Reads the entity by given data.
     *
     * @param typeId TypeId hash
     * @param id     the id of the entity.
     * @return
     * @throws ModelNotFoundException
     */
    BaseEntity getBaseEntity(Long typeId, Long id) throws ModelNotFoundException;

    /**
     * Returns the Class-Definition of the {@link BaseEntity} related to the given {@code typeId}.
     *
     * @param typeId TypeId hash
     * @return Class of the Entity
     * @throws ClassNotFoundException if no class is found for the given {@code typeId}
     * @throws NumberFormatException  if the value of {@code typeId} can't be cast to long
     */
    Class<? extends BaseEntity> getClassByTypeId(Long typeId) throws ClassNotFoundException, NumberFormatException;

    /**
     * Returns type id for the given {@link BaseEntity} class.
     *
     * @param aClass Any class extending {@link BaseEntity}
     * @return TypeId (hash)
     */
    static long getTypeIdFromBaseEntity(Class<? extends BaseEntity> aClass) {
        return aClass.getName().hashCode();
    }
}
