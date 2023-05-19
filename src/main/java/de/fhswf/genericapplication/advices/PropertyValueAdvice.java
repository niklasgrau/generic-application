package de.fhswf.genericapplication.advices;

import org.apache.commons.lang3.ClassUtils;
import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PropertyValueAdvice {

    @ResponseBody
    @ExceptionHandler(PropertyValueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handler(PropertyValueException e) {
        try {
            if (e.getMessage().contains("not-null property references a null or transient value")) {
                return String.format("The %s association cannot be disconnected. Exactly one %s is needed. Edit the association at the target entity.", ClassUtils.getClass(e.getEntityName()).getSimpleName(), e.getPropertyName());
            }
        } catch (ClassNotFoundException cnfe) {
            return String.format("The %s association cannot be disconnected. Exactly one %s is needed. Edit the association at the target entity.", e.getEntityName(), e.getPropertyName());
        }

        return e.getMessage();
    }
}