package de.fhswf.genericapplication.models.core;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(namespace = "core", name = "association")
@XmlAccessorType(XmlAccessType.FIELD)
public class PDAssociationMeta extends PDMemberMeta {
    @XmlAttribute
    @JsonProperty
    private String type;

    @XmlAttribute
    @JsonProperty
    private boolean required = false;

    @XmlAttribute
    @JsonProperty
    private String inverseOf;

    @XmlElementWrapper(namespace = "core", name = "validators")
    @XmlElement(namespace = "core", name = "validator")
    @JsonProperty
    private List<ValidatorMeta> validators = new ArrayList<>();

    @XmlAttribute
    @JsonProperty
    private String cardinality;
}
