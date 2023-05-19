package de.fhswf.genericapplication.exceptions;

public class ModelNotFoundException extends RuntimeException {
    public ModelNotFoundException(Long id) {
        super(String.format("Could not find model with ID '%d'", id));
    }

    public ModelNotFoundException(Long typeId, Throwable cause) {
        super(String.format("Could not find model of type '%s'", typeId), cause);
    }

    public ModelNotFoundException(Long typeId, Long id) {
        super(String.format("Could not find model of type '%s' with ID '%d'", typeId, id));
    }

    public ModelNotFoundException(Long typeId, Long id, Throwable cause) {
        super(String.format("Could not find model of type '%s' with ID '%d'", typeId, id), cause);
    }
}