package de.fhswf.genericapplication.dto.requests;

import javax.persistence.criteria.Expression;

/**
 * List of possible operators for a filter operation on generic entities.
 * The operator is the name of the {@link javax.persistence.criteria.CriteriaBuilder} method to call.
 * The paramTypes is a list of the necessary parameter of the operator method.
 *
 * @author Niklas Grau
 */
public enum FilterOperatorET {
    LIKE("like", Expression.class, String.class),
    NOT_LIKE("notLike", Expression.class, String.class),
    EQUAL("equal", Expression.class, Object.class),
    NOT_EQUAL("notEqual", Expression.class, Object.class),
    GREATER_THAN("greaterThan", Expression.class, Comparable.class),
    GREATER_OR_EQUAL("greaterThanOrEqualTo", Expression.class, Comparable.class),
    LESS_THAN("lessThan", Expression.class, Comparable.class),
    LESS_OR_EQUAL("lessThanOrEqualTo", Expression.class, Comparable.class),
    IN("in", Expression.class),
    IS_TRUE("isTrue", Expression.class),
    IS_FALSE("isFalse", Expression.class),
    ;

    private final String operator;
    private final Class<?>[] paramTypes;

    FilterOperatorET(String operator, Class<?>... paramTypes) {
        this.operator = operator;
        this.paramTypes = paramTypes;
    }

    @Override
    public String toString() {
        return this.operator;
    }

    public String getOperator() {
        return operator;
    }

    public Class<?>[] getParamTypes() {
        return paramTypes;
    }
}
