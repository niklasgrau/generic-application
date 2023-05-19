package de.fhswf.genericapplication.models.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.fhswf.genericapplication.models.BaseEntity;
import de.fhswf.genericapplication.serializers.MultiLanguageLabelSerializer;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(namespace = "core", name = "type")
@XmlAccessorType(XmlAccessType.FIELD)
public class PDTypeMeta extends PDMeta {
    @XmlAttribute
    @JsonProperty
    private Long typeId;

    @XmlAttribute
    @JsonProperty
    @JsonSerialize(using = MultiLanguageLabelSerializer.class)
    private String labelPlural;

    @XmlAttribute
    @JsonProperty
    private String icon;

    @XmlAttribute
    @JsonProperty
    private String objectStringifier;

    @XmlAttribute
    @JsonProperty
    private String subsystem;

    @XmlAttribute
    @JsonProperty
    private String base = BaseEntity.class.getSimpleName();

    @XmlAttribute
    @JsonProperty
    private boolean singleton = false;

    @XmlAttribute
    @JsonProperty
    private boolean masterData = false;

    @XmlElementWrapper(namespace = "core", name = "attributes")
    @XmlElement(namespace = "core", name = "attribute")
    @JsonProperty
    private List<PDAttributeMeta> attributes = new ArrayList<>();

    @XmlElementWrapper(namespace = "core", name = "associations")
    @XmlElement(namespace = "core", name = "association")
    @JsonProperty
    private List<PDAssociationMeta> associations = new ArrayList<>();

    @XmlElementWrapper(namespace = "core", name = "operations")
    @XmlElement(namespace = "core", name = "operation")
    @JsonProperty
    private List<PDOperationMeta> operations = new ArrayList<>();
}
