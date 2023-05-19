package de.fhswf.genericapplication.models.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.fhswf.genericapplication.serializers.MultiLanguageLabelSerializer;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "core", name = "validator")
public class ValidatorMeta {
    @XmlAttribute
    @JsonProperty
    @JsonSerialize(using = MultiLanguageLabelSerializer.class)
    private String errorMessage;

    @XmlAttribute
    @JsonProperty
    private String expression;
}
