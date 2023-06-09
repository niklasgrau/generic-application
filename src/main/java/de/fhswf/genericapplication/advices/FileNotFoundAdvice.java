package de.fhswf.genericapplication.advices;

import de.fhswf.genericapplication.exceptions.FileNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class FileNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(FileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handler(FileNotFoundException e) {
        return e.getMessage();
    }
}
