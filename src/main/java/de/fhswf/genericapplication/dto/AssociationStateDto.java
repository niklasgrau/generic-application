package de.fhswf.genericapplication.dto;

import java.util.List;

/**
 * The DTO for the association states of an entity. One association state manages one specific association of an entity.
 * Each association states consist of three different lists of {@link GenericEntityDto}: connected, disconnected or deleted
 * association entities. Each element of the lists, contains only typeId and ID of the entity - no property bag.
 *
 * @author Niklas Grau
 */
public class AssociationStateDto {
    private List<GenericEntityDto> connected;

    private List<GenericEntityDto> disconnected;

    private List<GenericEntityDto> deleted;

    public List<GenericEntityDto> getConnected() {
        return connected;
    }

    public void setConnected(List<GenericEntityDto> connected) {
        this.connected = connected;
    }

    public List<GenericEntityDto> getDisconnected() {
        return disconnected;
    }

    public void setDisconnected(List<GenericEntityDto> disconnected) {
        this.disconnected = disconnected;
    }

    public List<GenericEntityDto> getDeleted() {
        return deleted;
    }

    public void setDeleted(List<GenericEntityDto> deleted) {
        this.deleted = deleted;
    }
}
