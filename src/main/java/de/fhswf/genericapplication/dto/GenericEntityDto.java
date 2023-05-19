package de.fhswf.genericapplication.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

/**
 * The DTO of an entity. Always consists of the entity's typeId, ID and its properties. The properties can only contain
 * simple data types, for example primitive types and their wrapper classes, and ManyToOne associations. These
 * associations do only consist of the entity's typeId and ID - no property bag.
 *
 * @author Niklas Grau
 */
public class GenericEntityDto {
    private Long typeId;

    private Long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, ?> properties;

    public GenericEntityDto(Long typeId, Long id, Map<String, ?> properties) {
        this.typeId = typeId;
        this.id = id;
        this.properties = properties;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<String, ?> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, ?> properties) {
        this.properties = properties;
    }
}
