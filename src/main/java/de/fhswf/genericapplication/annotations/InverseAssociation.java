package de.fhswf.genericapplication.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to declare the name of the inverse relation property.
 *
 * @author Kevin Link
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface InverseAssociation {

    /**
     * Returns the name of the property to access the association from the target's entity class.
     * This provides the possibility to traverse between the entity's association via reflection.
     *
     * @return the property name to access the association.
     */
    String inverseRelationName();
}
