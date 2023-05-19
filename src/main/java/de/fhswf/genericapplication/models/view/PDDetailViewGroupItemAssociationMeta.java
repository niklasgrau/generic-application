package de.fhswf.genericapplication.models.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "view", name = "item")
@XmlAccessorType(XmlAccessType.FIELD)
public class PDDetailViewGroupItemAssociationMeta extends PDDetailViewGroupItemMeta {
    private PDListViewMeta listView;
}
