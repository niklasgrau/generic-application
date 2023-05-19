package de.fhswf.genericapplication.models.view;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(namespace = "view", name = "detailView")
@XmlAccessorType(XmlAccessType.FIELD)
public class PDDetailViewMeta extends PDViewMeta {
    @XmlElements({
            @XmlElement(namespace = "view", name = "group", type = PDDetailViewGroupMeta.class),
            @XmlElement(namespace = "view", name = "row", type = PDDetailViewGroupMeta.class),
            @XmlElement(namespace = "view", name = "tab", type = PDDetailViewGroupMeta.class),
            @XmlElement(namespace = "view", name = "card", type = PDDetailViewGroupMeta.class),
            @XmlElement(namespace = "view", name = "tabGroup", type = PDDetailViewGroupMeta.class),
    })
    @JsonProperty
    @JsonAlias({"group", "row", "tab", "card", "tabGroup"})
    private List<PDDetailViewGroupMeta> groups = new ArrayList<>();

    @XmlAttribute
    @JsonProperty
    private String type;

    @XmlAttribute
    @JsonProperty
    private String typeId;
}
