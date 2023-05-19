package de.fhswf.genericapplication.models.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.fhswf.genericapplication.models.core.PDMeta;
import de.fhswf.genericapplication.models.core.PDOperationMeta;

import javax.xml.bind.annotation.*;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@XmlRootElement(namespace = "view")
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class PDViewMeta extends PDMeta {
    @XmlAttribute(name = "name", namespace = "uiFactory")
    @JsonProperty
    private String uiFactory;

    @XmlAnyAttribute
    @JsonIgnore
    protected Map<QName, String> anyAttributes = new HashMap<>();

    @JsonProperty
    protected Map<String, String> uiFactoryParams() {
        return anyAttributes.entrySet().stream()
                .filter(entry -> entry.getKey().getPrefix().equalsIgnoreCase("uiFactory"))
                .collect(Collectors.toMap(entry -> entry.getKey().getLocalPart(), Map.Entry::getValue));
    }

    @XmlElementWrapper(namespace = "core", name = "operations")
    @XmlElement(namespace = "core", name = "operation")
    protected List<PDOperationMeta> operations = new ArrayList<>();
}
