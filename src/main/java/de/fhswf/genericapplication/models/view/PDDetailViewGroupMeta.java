package de.fhswf.genericapplication.models.view;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(namespace = "view", name = "group")
@XmlAccessorType(XmlAccessType.FIELD)
public class PDDetailViewGroupMeta extends PDUIElementMeta {
    private PDDetailViewMeta parent;

    @XmlElement(namespace = "view", name = "group", type = PDDetailViewGroupMeta.class)
    @JsonProperty
    private List<PDDetailViewGroupMeta> groups = new ArrayList<>();

    @XmlElement(namespace = "view", name = "item", type = PDDetailViewGroupItemMeta.class)
    @JsonProperty
    private List<PDDetailViewGroupItemMeta> items = new ArrayList<>();
}
