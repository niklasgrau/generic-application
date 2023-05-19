package de.fhswf.genericapplication.models.view;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(namespace = "view", name = "listView")
@XmlAccessorType(XmlAccessType.FIELD)
public class PDListViewMeta extends PDViewMeta {
    @XmlElementWrapper(namespace = "view", name = "columns")
    @XmlElement(namespace = "view", name = "column")
    @JsonProperty
    private List<PDColumn> columns = new ArrayList<>();
}
