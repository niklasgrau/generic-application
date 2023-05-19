package de.fhswf.genericapplication.models.core;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "core", name = "operation")
@XmlAccessorType(XmlAccessType.FIELD)
public class PDOperationMeta extends PDMemberMeta {
    @XmlAttribute
    @JsonProperty
    private String icon;
}
