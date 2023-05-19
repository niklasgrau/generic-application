package de.fhswf.genericapplication.models.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.fhswf.genericapplication.models.view.PDDetailViewMeta;
import de.fhswf.genericapplication.models.view.PDListViewMeta;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(namespace = "core", name = "application")
@XmlAccessorType(XmlAccessType.FIELD)
public class PDApplication extends PDMeta {

    @XmlAttribute
    @JsonProperty
    private Long version;

    @XmlElementWrapper(namespace = "core", name = "subsystems")
    @XmlElement(namespace = "core", name = "subsystem")
    @JsonProperty("subsystems")
    private List<Subsystem> subsystems = new ArrayList<>();

    @XmlElementWrapper(namespace = "core", name = "navigation")
    @XmlElement(namespace = "core", name = "navigationItem")
    @JsonProperty("navigation")
    private List<NavigationItem> navigationMenu = new ArrayList<>();

    @XmlElementWrapper(namespace = "core", name = "types")
    @XmlElement(namespace = "core", name = "type")
    @JsonProperty("types")
    private List<PDTypeMeta> types = new ArrayList<>();

    @XmlElementWrapper(namespace = "core", name = "enums")
    @XmlElement(namespace = "core", name = "enum")
    @JsonProperty("enums")
    private List<PDEnumMeta> enums = new ArrayList<>();

    @XmlElementWrapper(namespace = "core", name = "listViews")
    @XmlElement(namespace = "view", name = "listView")
    @JsonProperty("listViews")
    private List<PDListViewMeta> listViews = new ArrayList<>();

    @XmlElementWrapper(namespace = "core", name = "detailViews")
    @XmlElement(namespace = "view", name = "detailView")
    @JsonProperty("detailViews")
    private List<PDDetailViewMeta> detailViews = new ArrayList<>();
}
