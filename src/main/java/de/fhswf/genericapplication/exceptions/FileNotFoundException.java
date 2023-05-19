package de.fhswf.genericapplication.exceptions;

public class FileNotFoundException extends RuntimeException {
    public FileNotFoundException() {
        super("File could not be found.");
    }

    public FileNotFoundException(String message) {
        super(message);
    }

    public FileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileNotFoundException(Throwable cause) {
        super(cause);
    }
}
