package de.fhswf.genericapplication.exceptions;

public class DateParseException extends RuntimeException {
    public DateParseException(String date) {
        super(String.format("Could parse date string '%s'", date));
    }

    public DateParseException(String date, Throwable cause) {
        super(String.format("Could parse date string '%s'", date), cause);
    }
}