package de.fhswf.genericapplication.models.core;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(namespace = "core", name = "enum")
@XmlAccessorType(XmlAccessType.FIELD)
public class PDEnumMeta extends PDMeta {
    @XmlElement(namespace = "core", name = "value")
    @JsonProperty
    private List<PDMeta> values = new ArrayList<>();
}
