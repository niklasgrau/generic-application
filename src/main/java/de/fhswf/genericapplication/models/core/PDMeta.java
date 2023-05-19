package de.fhswf.genericapplication.models.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.fhswf.genericapplication.serializers.MultiLanguageLabelSerializer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(namespace = "core")
@XmlAccessorType(XmlAccessType.FIELD)
public class PDMeta implements Serializable {
    @XmlAttribute
    @JsonProperty
    private String name;

    @XmlAttribute
    @JsonProperty
    @JsonSerialize(using = MultiLanguageLabelSerializer.class)
    private String label;
}
