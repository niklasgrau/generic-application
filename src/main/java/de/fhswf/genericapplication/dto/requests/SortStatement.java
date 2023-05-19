package de.fhswf.genericapplication.dto.requests;

import java.util.List;

/**
 * The DTO for sorting statements. A statement contains of a list of {@link SortItem}.
 * Each sort item consists of a property name to sort by and the sorting direction.
 *
 * @author Niklas Grau
 */
public class SortStatement {
    private List<SortItem> items;

    public List<SortItem> getItems() {
        return items;
    }

    public void setItems(List<SortItem> items) {
        this.items = items;
    }
}
