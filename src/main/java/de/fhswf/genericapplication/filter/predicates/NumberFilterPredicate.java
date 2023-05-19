package de.fhswf.genericapplication.filter.predicates;

import de.fhswf.genericapplication.dto.requests.FilterMember;
import de.fhswf.genericapplication.models.BaseEntity;
import org.springframework.util.NumberUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Resolves filter on number value to predicate.
 *
 * @author Niklas Grau, Kevin Link
 */
public class NumberFilterPredicate implements FilterPredicate {
    @Override
    public <T> T convertToTargetDataType(Class<T> type, Object obj) {
        if (obj == null) {
            return type.cast(null);
        }

        String value = String.valueOf(obj);
        if (!value.matches("-?\\d+(\\.\\d+)?")) {
            throw new NumberFormatException("Failed to convert number value to number type.");
        }

        return type.cast(NumberUtils.parseNumber(value, type.asSubclass(Number.class)));
    }

    @Override
    public <E extends BaseEntity> Predicate toPredicate(Root<E> root, CriteriaBuilder builder, Method method, Field entityMemberField, FilterMember filterMember) throws InvocationTargetException, IllegalAccessException {
        return (Predicate) method.invoke(
                builder,
                root.get(filterMember.getMemberName()),
                this.convertToTargetDataType(root.get(filterMember.getMemberName()).getJavaType(), filterMember.getValue())
        );
    }
}
