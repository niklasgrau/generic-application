package de.fhswf.genericapplication.exceptions;

public class ModelSavingException extends RuntimeException {
    public ModelSavingException(String message) {
        super(message);
    }

    public ModelSavingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ModelSavingException(Throwable cause) {
        super(cause);
    }
}
