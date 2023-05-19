package de.fhswf.genericapplication.models.core;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(namespace = "core", name = "subsystem")
@XmlAccessorType(XmlAccessType.FIELD)
public class Subsystem extends PDMeta {

    @XmlAttribute
    @JsonProperty
    private String icon;

    @XmlElement(namespace = "core", name = "subsystem")
    @JsonProperty("subsystems")
    private List<Subsystem> children = new ArrayList<>();
}
