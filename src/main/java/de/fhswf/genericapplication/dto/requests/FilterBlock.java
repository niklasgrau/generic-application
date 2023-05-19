package de.fhswf.genericapplication.dto.requests;

import java.util.List;

/**
 * The DTO for a filter block. It consists of a filter operator and a list of {@link FilterMember}.
 * Each filter member consists of a name, filter operator and the value to filter by.
 *
 * @author Niklas Grau
 */
public class FilterBlock {
    private FilterBlockOperatorET operator;
    private List<FilterMember> statements;

    public FilterBlockOperatorET getOperator() {
        return operator;
    }

    public void setOperator(FilterBlockOperatorET operator) {
        this.operator = operator;
    }

    public List<FilterMember> getStatements() {
        return statements;
    }

    public void setStatements(List<FilterMember> statements) {
        this.statements = statements;
    }
}
