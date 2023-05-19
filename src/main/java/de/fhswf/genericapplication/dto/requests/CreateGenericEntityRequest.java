package de.fhswf.genericapplication.dto.requests;

import de.fhswf.genericapplication.dto.AssociationStateDto;
import de.fhswf.genericapplication.dto.GenericEntityDto;

import java.util.Map;

/**
 * The DTO for create requests of an entity. A request consists of the origin entity as {@link GenericEntityDto} and
 * the association states as a map of {@link AssociationStateDto}. The map key for each association state is the
 * association name (association property of the entity).
 *
 * @author Niklas Grau
 */
public class CreateGenericEntityRequest extends Request {
    private GenericEntityDto entity;

    private Map<String, AssociationStateDto> associationStates;

    public GenericEntityDto getEntity() {
        return entity;
    }

    public void setEntity(GenericEntityDto entity) {
        this.entity = entity;
    }

    public Map<String, AssociationStateDto> getAssociationStates() {
        return associationStates;
    }

    public void setAssociationStates(Map<String, AssociationStateDto> associationStates) {
        this.associationStates = associationStates;
    }
}
