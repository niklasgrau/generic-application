package de.fhswf.genericapplication.filter.predicates;

import de.fhswf.genericapplication.dto.requests.FilterMember;
import de.fhswf.genericapplication.models.BaseEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Resolves filter on boolean value to predicate.
 *
 * @author Niklas Grau, Kevin Link
 */
public class BooleanFilterPredicate implements FilterPredicate {
    @Override
    public <T> T convertToTargetDataType(Class<T> type, Object obj) {
        return type.cast(obj);
    }

    @Override
    public <E extends BaseEntity> Predicate toPredicate(Root<E> root, CriteriaBuilder builder, Method method, Field entityMemberField, FilterMember filterMember) throws InvocationTargetException, IllegalAccessException {
        return (Predicate) method.invoke(
                builder,
                root.get(filterMember.getMemberName())
        );
    }
}
