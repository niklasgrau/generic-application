package de.fhswf.genericapplication.services;

import de.fhswf.genericapplication.dto.AssociationStateDto;
import de.fhswf.genericapplication.dto.GenericEntityDto;
import de.fhswf.genericapplication.dto.requests.FilterStatement;
import de.fhswf.genericapplication.models.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Service layer to handle CRUD operations for any kind of {@link BaseEntity}.
 *
 * @author Niklas Grau
 */
public interface GenericEntityService {
    /**
     * Creates an entity.
     *
     * @param entityDto            the DTO representation of a {@link BaseEntity}.
     * @param associationStateDtos the entity's associations to connect, disconnect or delete.
     * @return the {@link GenericEntityDto} representation of the created {@link BaseEntity}.
     */
    GenericEntityDto create(GenericEntityDto entityDto, Map<String, AssociationStateDto> associationStateDtos);

    /**
     * Reads an entity by its type and id.
     *
     * @param typeId identifies the targets {@link BaseEntity} class type.
     * @param id     unique identifier of the {@link BaseEntity}.
     * @return the {@link GenericEntityDto} representation of the found {@link BaseEntity}.
     */
    GenericEntityDto get(Long typeId, Long id, List<String> propertySelection);

    /**
     * Lists all instances of the entity type by the given filter.
     *
     * @param typeId            identifies the targets {@link BaseEntity} class type.
     * @param propertySelection a list of property field names to retrieve.
     * @param pagination        contains pagination data, like page number and size.
     * @param filter            a list of and/or filters containing statements with operators like "equals" or "like".
     * @return a paginated list of {@link GenericEntityDto} representation of the found {@link BaseEntity}.
     */
    Page<GenericEntityDto> list(Long typeId, List<String> propertySelection, Pageable pagination, FilterStatement filter);

    /**
     * Updates an entity.
     *
     * @param id                   unique identifier of the {@link BaseEntity}.
     * @param entityDto            the DTO representation of a {@link BaseEntity}.
     * @param associationStateDtos the entity's associations to connect, disconnect or delete.
     * @return the {@link GenericEntityDto} representation of the updated {@link BaseEntity}.
     */
    GenericEntityDto update(Long id, GenericEntityDto entityDto, Map<String, AssociationStateDto> associationStateDtos);

    /**
     * Deletes the entity with the given type and id.
     *
     * @param typeId identifies the targets {@link BaseEntity} class type.
     * @param id     unique identifier of the {@link BaseEntity}.
     */
    void delete(Long typeId, Long id);
}
