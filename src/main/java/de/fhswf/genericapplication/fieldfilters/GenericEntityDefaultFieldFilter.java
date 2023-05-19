package de.fhswf.genericapplication.fieldfilters;

import de.fhswf.genericapplication.models.BaseEntity;
import org.springframework.data.util.ReflectionUtils.DescribedFieldFilter;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;

/**
 * Filters all properties, which are not a simple property, for example primitive types and their wrapper classes.
 *
 * @author Niklas Grau
 */
public class GenericEntityDefaultFieldFilter implements DescribedFieldFilter {
    @Override
    public boolean matches(Field field) {
        Class<?> fieldType = field.getType();
        if (Collection.class.isAssignableFrom(field.getType())) {
            fieldType = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
        }

        return !Modifier.isStatic(field.getModifiers())
                && !Modifier.isFinal(field.getModifiers())
                && !field.getName().equals("id")
                && !field.getName().equals("typeId")
                && !BaseEntity.class.isAssignableFrom(fieldType)
                ;
    }

    @Override
    public String getDescription() {
        return String.format("Default filter for %s", BaseEntity.class.getSimpleName());
    }
}
