package de.fhswf.genericapplication.advices;

import de.fhswf.genericapplication.exceptions.PropertyNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PropertyNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(PropertyNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handler(PropertyNotFoundException e) {
        return e.getMessage();
    }
}