package de.fhswf.genericapplication.filter.predicates;

import de.fhswf.genericapplication.dto.requests.FilterMember;
import de.fhswf.genericapplication.models.BaseEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Resolves filter on collection value to predicate.
 *
 * @author Niklas Grau, Kevin Link
 */
public class CollectionFilterPredicate implements FilterPredicate {
    @Override
    public <T> T convertToTargetDataType(Class<T> type, Object obj) {
        String[] values = (String.valueOf(obj)).split(",");
        Set<?> ids = Arrays.stream(values)
                .map(String::trim)
                .map(Long::valueOf)
                .collect(Collectors.toSet());

        return type.cast(ids);
    }

    @Override
    public <E extends BaseEntity> Predicate toPredicate(Root<E> root, CriteriaBuilder builder, Method method, Field entityMemberField, FilterMember filterMember) throws InvocationTargetException, IllegalAccessException {
        Expression<Long> expression = root.get("id");
        return expression.in(this.convertToTargetDataType(Collection.class, filterMember.getValue()));
    }
}
