package de.fhswf.genericapplication.dto.requests;

/**
 * The DTO for sort items. An item consists of a property name to sort by and the sorting direction.
 *
 * @author Niklas Grau
 */
public class SortItem {
    private String propertyName;
    private boolean isAscending;

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public boolean isAscending() {
        return isAscending;
    }

    public void setIsAscending(boolean isAscending) {
        this.isAscending = isAscending;
    }
}
