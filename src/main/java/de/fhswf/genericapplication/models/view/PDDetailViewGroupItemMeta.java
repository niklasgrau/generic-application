package de.fhswf.genericapplication.models.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.fhswf.genericapplication.models.core.PDMemberMeta;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "view", name = "item")
@XmlAccessorType(XmlAccessType.FIELD)
public class PDDetailViewGroupItemMeta extends PDUIElementMeta {

    @JsonProperty
    private PDMemberMeta member;

    @XmlAttribute
    @JsonProperty
    private boolean disabled = false;
}
