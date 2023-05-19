package de.fhswf.genericapplication.exceptions;

public class MetadataException extends RuntimeException {

    public MetadataException() {
        super("Metadata could not be parsed or transformed.");
    }

    public MetadataException(String message) {
        super(message);
    }

    public MetadataException(String message, Throwable cause) {
        super(message, cause);
    }
}
