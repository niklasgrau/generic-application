package de.fhswf.genericapplication.filter.predicates;

import de.fhswf.genericapplication.dto.requests.FilterMember;
import de.fhswf.genericapplication.exceptions.DateParseException;
import de.fhswf.genericapplication.models.BaseEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Resolves filter on date value to predicate.
 *
 * @author Niklas Grau, Kevin Link
 */
public class DateFilterPredicate implements FilterPredicate {
    @Override
    public <T> T convertToTargetDataType(Class<T> type, Object obj) {
        String dateString = String.valueOf(obj);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");

        try {
            return type.cast(obj == null ? null : dateFormat.parse(dateString));
        } catch (ParseException e) {
            throw new DateParseException(dateString, e);
        }
    }

    @Override
    public <E extends BaseEntity> Predicate toPredicate(
            Root<E> root, CriteriaBuilder builder, Method method, Field field, FilterMember filter
    ) throws InvocationTargetException, IllegalAccessException {
        return (Predicate) method.invoke(
                builder,
                root.get(filter.getMemberName()),
                this.convertToTargetDataType(Date.class, filter.getValue())
        );
    }
}
