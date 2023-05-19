package de.fhswf.genericapplication.models.core;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(namespace = "core", name = "attribute")
@XmlAccessorType(XmlAccessType.FIELD)
public class PDAttributeMeta extends PDMemberMeta {
    @XmlAttribute
    @JsonProperty
    private String dataType = "String";

    @XmlAttribute
    @JsonProperty
    private boolean required = false;

    @XmlElementWrapper(namespace = "core", name = "validators")
    @XmlElement(namespace = "core", name = "validator")
    @JsonProperty
    private List<ValidatorMeta> validators = new ArrayList<>();

    @XmlAttribute
    @JsonProperty
    private String values;
}
