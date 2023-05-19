package de.fhswf.genericapplication.filter.data;

import de.fhswf.genericapplication.models.BaseEntity;

import java.util.Collection;
import java.util.Date;

/**
 * Enum of allowed data types to be defined via metadata and filtered by when fetching entities.
 *
 * @author Kevin Link
 */
public enum DataTypeET {
    STRING(String.class),
    NUMBER(Number.class),
    BOOLEAN(Boolean.class),
    DATE(Date.class),
    ENUM(Enum.class),
    COLLECTION(Collection.class),
    ENTITY(BaseEntity.class);

    private final Class<?> clazz;

    DataTypeET(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Class<?> getClazz() {
        return clazz;
    }
}
