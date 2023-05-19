package de.fhswf.genericapplication.models.view;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;
import java.util.stream.Collectors;

@XmlRootElement(namespace = "view", name = "column")
@XmlAccessorType(XmlAccessType.FIELD)
public class PDColumn extends PDDetailViewGroupItemMeta {
    private PDListViewMeta listView;

    @XmlAttribute(name = "default")
    @JsonProperty("default")
    private boolean isDefault = false;

    @XmlAttribute
    @JsonProperty
    private boolean sortable = true;

    @XmlAttribute
    @JsonProperty
    private boolean searchable = true;

    @XmlAttribute
    @JsonProperty
    private int width = 45;

    @XmlAttribute(name = "name", namespace = "formatterFactory")
    @JsonProperty
    private String formatterFactory;

    @JsonProperty
    public Map<String, String> formatterFactoryParams() {
        return anyAttributes.entrySet().stream()
                .filter(entry -> entry.getKey().getPrefix().equalsIgnoreCase("formatterFactory"))
                .collect(Collectors.toMap(entry -> entry.getKey().getLocalPart(), Map.Entry::getValue));
    }
}
