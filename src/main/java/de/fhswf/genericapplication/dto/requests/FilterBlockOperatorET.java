package de.fhswf.genericapplication.dto.requests;

import javax.persistence.criteria.Predicate;

/**
 * List of possible operators for a filter block operation on generic entities.
 * The operator connects the blocks and can be "and" or "or".
 * The paramTypes is a list of {@link Predicate} to connect by the operator.
 *
 * @author Niklas Grau
 */
public enum FilterBlockOperatorET {
    AND("and", Predicate[].class),
    OR("or", Predicate[].class),
    ;

    private final String operator;
    private final Class<?>[] paramTypes;

    FilterBlockOperatorET(String operator, Class<?>... paramTypes) {
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
