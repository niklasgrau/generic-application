package de.fhswf.genericapplication.exceptions;

public class PropertyTypeMismatchException extends RuntimeException {
    public PropertyTypeMismatchException(Class<?> clazz, Class<?> expected, Class<?> actual) {
        super(String.format("Property type mismatch of class type '%s'. Expected: '%s', actual: '%s'", clazz.getName(), expected.getName(), actual.getName()));
    }

    public PropertyTypeMismatchException(Class<?> clazz, Class<?> expected, Class<?> actual, Throwable cause) {
        super(String.format("Property type mismatch of class type '%s'. Expected: '%s', actual: '%s'", clazz.getName(), expected.getName(), actual.getName()), cause);
    }
}