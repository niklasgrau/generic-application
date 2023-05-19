package de.fhswf.genericapplication.fieldfilters;

import de.fhswf.genericapplication.models.BaseEntity;
import org.springframework.data.util.ReflectionUtils.DescribedFieldFilter;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;

/**
 * Filters all properties, which are not an instance of {@link BaseEntity} or {@link Collection<BaseEntity>}.
 *
 * @author Niklas Grau
 */
public class GenericEntityAssociationFieldFilter implements DescribedFieldFilter {
    @Override
    public boolean matches(Field field) {
        Class<?> fieldType = field.getType();
        if (Collection.class.isAssignableFrom(field.getType())) {
            fieldType = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
        }

        return BaseEntity.class.isAssignableFrom(fieldType);
    }

    @Override
    public String getDescription() {
        return String.format("Association filter for %s", BaseEntity.class.getSimpleName());
    }
}
