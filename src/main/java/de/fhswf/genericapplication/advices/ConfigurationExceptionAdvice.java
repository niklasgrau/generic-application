package de.fhswf.genericapplication.advices;

import de.fhswf.genericapplication.exceptions.ConfigurationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ConfigurationExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(ConfigurationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handler(ConfigurationException e) {
        return e.getMessage();
    }
}