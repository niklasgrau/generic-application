package de.fhswf.genericapplication.dto.requests;

/**
 * The DTO for a filter block. It consists of a name, filter operator and the value to filter by.
 * The member name has to be the name of the entities attribute. Possible operators are listed in {@link FilterOperatorET}.
 *
 * @author Niklas Grau
 */
public class FilterMember {
    private String memberName;

    private FilterOperatorET operator;

    private Object value;

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public FilterOperatorET getOperator() {
        return operator;
    }

    public void setOperator(FilterOperatorET operator) {
        this.operator = operator;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
