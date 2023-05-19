package de.fhswf.genericapplication.filter.data;

import de.fhswf.genericapplication.dto.requests.FilterBlockOperatorET;

import javax.persistence.criteria.Predicate;

/**
 * Data object to handle filtering by predicates.
 *
 * @author Niklas Grau
 */
public class FilterPair {
    private FilterBlockOperatorET operator;
    private Predicate predicate;

    public FilterPair(FilterBlockOperatorET operator, Predicate predicate) {
        this.operator = operator;
        this.predicate = predicate;
    }

    public FilterBlockOperatorET getOperator() {
        return operator;
    }

    public void setOperator(FilterBlockOperatorET operator) {
        this.operator = operator;
    }

    public Predicate getPredicate() {
        return predicate;
    }

    public void setPredicate(Predicate predicate) {
        this.predicate = predicate;
    }
}
