package de.fhswf.genericapplication.advices;

import de.fhswf.genericapplication.exceptions.ModelConstraintException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ModelConstraintAdvice {

    @ResponseBody
    @ExceptionHandler(ModelConstraintException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handler(ModelConstraintException e) {
        return e.getMessage();
    }
}