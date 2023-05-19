package de.fhswf.genericapplication.exceptions;

public class PropertyNotFoundException extends RuntimeException {
    public PropertyNotFoundException(String name) {
        super(String.format("Could not find property '%s'", name));
    }

    public PropertyNotFoundException(String name, Throwable cause) {
        super(String.format("Could not find property '%s'", name), cause);
    }
}