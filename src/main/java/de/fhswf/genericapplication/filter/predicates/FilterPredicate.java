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
 * Interface to allow conversion of filters to predicates
 *
 * @author Niklas Grau, Kevin Link
 */
public interface FilterPredicate {

    /**
     * Converts the current value {@code obj} to the target class {@code type}.
     *
     * @param type Target class
     * @param obj  Source value
     * @param <T>  Class of conversion and return value
     * @return Returns the converted value
     */
    <T> T convertToTargetDataType(Class<T> type, Object obj);

    /**
     * Invokes a {@link CriteriaBuilder} method to create a predicate to filter.
     *
     * @param root              Root of the criteria query.
     * @param builder           Instance of the criteria builder to invoke.
     * @param method            Method to invoke of the builder.
     * @param entityMemberField Field to filter by.
     * @param filterMember      Filter value.
     * @param <E>               BaseEntity
     * @return Returns a predicate determined by the given parameters.
     * @throws InvocationTargetException when method invocation fails by value
     * @throws IllegalAccessException    when method invocation fails by access
     */
    <E extends BaseEntity> Predicate toPredicate(Root<E> root, CriteriaBuilder builder, Method method, Field entityMemberField, FilterMember filterMember) throws InvocationTargetException, IllegalAccessException;
}
