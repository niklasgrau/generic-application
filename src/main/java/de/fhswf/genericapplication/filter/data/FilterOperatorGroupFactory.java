package de.fhswf.genericapplication.filter.data;

import de.fhswf.genericapplication.dto.requests.FilterOperatorET;

/**
 * Factory to create groups of {@link FilterOperatorET} to determine allowed operators by {@link DataTypeET}.
 *
 * @author Kevin Link
 */
public class FilterOperatorGroupFactory {
    public static FilterOperatorET[] createOperatorGroup(DataTypeET dataType) {
        return switch (dataType) {
            case STRING -> createStringOperatorGroup();
            case NUMBER -> createNumberOperatorGroup();
            case BOOLEAN -> createBooleanOperatorGroup();
            case DATE -> createDateOperatorGroup();
            case ENUM -> createEnumOperatorGroup();
            case COLLECTION -> createCollectionOperatorGroup();
            case ENTITY -> createEntityOperatorGroup();
            default -> throw new RuntimeException("Unsupported data type " + dataType + " given.");
        };
    }

    public static FilterOperatorET[] createStringOperatorGroup() {
        return new FilterOperatorET[]{
                FilterOperatorET.EQUAL,
                FilterOperatorET.NOT_EQUAL,
                FilterOperatorET.LIKE,
                FilterOperatorET.NOT_LIKE
        };
    }

    public static FilterOperatorET[] createNumberOperatorGroup() {
        return new FilterOperatorET[]{
                FilterOperatorET.EQUAL,
                FilterOperatorET.NOT_EQUAL,
                FilterOperatorET.GREATER_THAN,
                FilterOperatorET.GREATER_OR_EQUAL,
                FilterOperatorET.LESS_THAN,
                FilterOperatorET.LESS_OR_EQUAL,
                FilterOperatorET.IN
        };
    }

    public static FilterOperatorET[] createBooleanOperatorGroup() {
        return new FilterOperatorET[]{
                FilterOperatorET.IS_TRUE,
                FilterOperatorET.IS_FALSE
        };
    }

    public static FilterOperatorET[] createDateOperatorGroup() {
        return new FilterOperatorET[]{
                FilterOperatorET.EQUAL,
                FilterOperatorET.NOT_EQUAL,
                FilterOperatorET.GREATER_THAN,
                FilterOperatorET.GREATER_OR_EQUAL,
                FilterOperatorET.LESS_THAN,
                FilterOperatorET.LESS_OR_EQUAL
        };
    }

    public static FilterOperatorET[] createEnumOperatorGroup() {
        return new FilterOperatorET[]{
                FilterOperatorET.EQUAL,
                FilterOperatorET.NOT_EQUAL
        };
    }

    private static FilterOperatorET[] createCollectionOperatorGroup() {
        return new FilterOperatorET[]{
                FilterOperatorET.EQUAL,
                FilterOperatorET.IN
        };
    }

    private static FilterOperatorET[] createEntityOperatorGroup() {
        return new FilterOperatorET[]{
                FilterOperatorET.EQUAL,
                FilterOperatorET.NOT_EQUAL,
                FilterOperatorET.IN
        };
    }
}