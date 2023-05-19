package de.fhswf.genericapplication.services;

import de.fhswf.genericapplication.models.BaseEntity;

/**
 * Service layer to handle association states of a {@link BaseEntity} by connecting, disconnecting or deleting associations.
 *
 * @author Niklas Grau
 */
public interface AssociationStateService {
    /**
     * Connects an entity to another.
     *
     * @param typeId       identifies the association {@link BaseEntity} class type.
     * @param id           unique identifier of the associations {@link BaseEntity}.
     * @param propertyName property of the associations entity class to use for connection.
     * @param association  value to connect to the association entity.
     * @return the connected association entity.
     */
    BaseEntity connect(Long typeId, Long id, String propertyName, BaseEntity association);

    /**
     * Connects an entity to another.
     *
     * @param entity       the entity to connect another entity to.
     * @param propertyName property of the associations entity class to use for connection.
     * @param association  value to connect to the association entity.
     * @return the connected association entity.
     */
    BaseEntity connect(BaseEntity entity, String propertyName, BaseEntity association);

    /**
     * Disconnects an entity from another.
     *
     * @param typeId       identifies the association {@link BaseEntity} class type.
     * @param id           unique identifier of the associations {@link BaseEntity}.
     * @param propertyName property of the associations entity class to use for connection.
     * @param association  value to disconnect from the association entity.
     * @return the disconnected association entity.
     */
    BaseEntity disconnect(Long typeId, Long id, String propertyName, BaseEntity association);

    /**
     * Disconnects an entity from another.
     *
     * @param entity       the entity to disconnect another entity to.
     * @param propertyName property of the associations entity class to use for connection.
     * @param association  value to disconnect from the association entity.
     * @return the connected association entity.
     */
    BaseEntity disconnect(BaseEntity entity, String propertyName, BaseEntity association);

    /**
     * Deletes an entity.
     *
     * @param typeId identifies the association {@link BaseEntity} class type.
     * @param id     unique identifier of the associations {@link BaseEntity}.
     */
    void delete(Long typeId, Long id);

    /**
     * Deletes an entity.
     *
     * @param entity the entity to delete.
     */
    void delete(BaseEntity entity);
}
