package de.fhswf.genericapplication.exceptions;

import de.fhswf.genericapplication.models.BaseEntity;

public class ModelConstraintException extends RuntimeException {

    public ModelConstraintException(Long typeId, Long id, String attributeName, Throwable cause) {
        super(String.format("Could not delete model of type '%d' with id %d, due to integrity constraint violation on field %s.", typeId, id, attributeName), cause);
    }

    public ModelConstraintException(BaseEntity entity, String attributeName, Throwable cause) {
        super(String.format("Could not delete model of type %s with id %d, due to integrity constraint violation on field %s.", entity.getClass().getSimpleName(), entity.getId(), attributeName), cause);
    }
}
