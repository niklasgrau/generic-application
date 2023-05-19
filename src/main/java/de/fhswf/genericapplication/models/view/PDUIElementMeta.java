package de.fhswf.genericapplication.models.view;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "view")
public abstract class PDUIElementMeta extends PDViewMeta {
    @XmlAttribute
    @JsonProperty
    private boolean labelVisible = true;

    @XmlAttribute
    @JsonProperty
    private int colSM = 12;

    @XmlAttribute
    @JsonProperty
    private int colMD = 12;

    @XmlAttribute
    @JsonProperty
    private int colLG = 12;

    @XmlAttribute
    @JsonProperty
    private int colOffset = 0;
}
