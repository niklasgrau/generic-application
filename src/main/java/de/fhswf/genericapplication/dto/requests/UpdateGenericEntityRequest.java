package de.fhswf.genericapplication.dto.requests;

import de.fhswf.genericapplication.dto.AssociationStateDto;
import de.fhswf.genericapplication.dto.GenericEntityDto;

/**
 * The DTO for update requests of an entity. A request consists of the origin entity as {@link GenericEntityDto} and
 * the association states as a map of {@link AssociationStateDto}. The map key for each association state is the
 * association name (association property of the entity). Additionally, the ID of the entity is required.
 *
 * @author Niklas Grau
 */
public class UpdateGenericEntityRequest extends CreateGenericEntityRequest {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
