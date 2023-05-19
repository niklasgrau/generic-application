package de.fhswf.genericapplication.repositories;

import de.fhswf.genericapplication.dto.requests.FilterStatement;
import de.fhswf.genericapplication.models.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Interface for CRUD operations on a {@link BaseEntity}.
 *
 * @author Niklas Grau
 */
public interface GenericEntityRepository {
    /**
     * Creates an entity.
     *
     * @param entity must not be null.
     * @return the created entity represented as {@link E}.
     */
    @Transactional
    <E extends BaseEntity> E create(E entity);

    /**
     * Creates a specific amount of entities.
     *
     * @param entities must not be null.
     * @return the created entity represented as {@link E}.
     */
    @Transactional
    <E extends BaseEntity> List<E> createAll(Iterable<E> entities);

    /**
     * Retrieves an entity by type and id.
     *
     * @param entityClass the class type of the entity.
     * @param id          unique identifier of the {@link BaseEntity}.
     * @return the found entity represented as {@link E}.
     */
    <E extends BaseEntity> E findById(Class<E> entityClass, Long id);

    /**
     * Returns all instances of the requested class type.
     *
     * @param entityClass the class type of the entity.
     * @param pagination  contains pagination data, like page number and size.
     * @param filter      contains filter data, like blocks and operators, as {@link FilterStatement}
     * @return a paginated list of found entities represented as {@link E}.
     */
    <E extends BaseEntity> Page<E> findAll(
            Class<E> entityClass,
            Pageable pagination,
            FilterStatement filter
    );

    /**
     * Updates an entity.
     *
     * @param entity must not be null.
     * @return the updated entity represented as {@link E}.
     */
    @Transactional
    <E extends BaseEntity> E update(E entity);

    /**
     * Updates all given entities.
     *
     * @param entities must not be null.
     * @return the updated entity represented as {@link E}.
     */
    @Transactional
    <E extends BaseEntity> List<E> updateAll(Iterable<E> entities);

    /**
     * Deletes an entity.
     *
     * @param entity must not be null.
     */
    @Transactional
    <E extends BaseEntity> void delete(E entity);

    /**
     * Deletes an entity by type and id.
     *
     * @param entityClass the class type of the entity.
     * @param id          unique identifier of the {@link BaseEntity}.
     */
    @Transactional
    <E extends BaseEntity> void deleteById(Class<E> entityClass, Long id);
}
