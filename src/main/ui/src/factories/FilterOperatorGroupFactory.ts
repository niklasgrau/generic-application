import {FilterOperatorET} from "@/models/runtime/filter/FilterOperatorET";
import {DataTypeET} from "@/models/core/DataTypeET";

/**
 * Factory to create groups by DataTypeET to determine allowed operations, represented by FilterOperatorET values.
 * @author Kevin Link
 */
export class FilterOperatorGroupFactory {

    public createOperatorGroup(dataType: DataTypeET): FilterOperatorET[] {
        switch (dataType) {
            case DataTypeET.STRING:
                return this.createStringOperatorGroup();
            case DataTypeET.NUMBER:
                return this.createNumberOperatorGroup();
            case DataTypeET.BOOLEAN:
                return this.createBooleanOperatorGroup();
            case DataTypeET.DATE:
                return this.createDateOperatorGroup();
            case DataTypeET.ENUM:
                return this.createEnumOperatorGroup();
            default:
                throw new Error(`Unsupported data type '${dataType}' given.`);
        }
    }

    public createStringOperatorGroup(): FilterOperatorET[] {
        return [
            FilterOperatorET.EQUAL,
            FilterOperatorET.NOT_EQUAL,
            FilterOperatorET.LIKE,
            FilterOperatorET.NOT_LIKE
        ];
    }

    public createNumberOperatorGroup(): FilterOperatorET[] {
        return [
            FilterOperatorET.EQUAL,
            FilterOperatorET.NOT_EQUAL,
            FilterOperatorET.GREATER_THAN,
            FilterOperatorET.GREATER_OR_EQUAL,
            FilterOperatorET.LESS_THAN,
            FilterOperatorET.LESS_OR_EQUAL
        ];
    }

    public createBooleanOperatorGroup(): FilterOperatorET[] {
        return [
            FilterOperatorET.IS_TRUE,
            FilterOperatorET.IS_FALSE
        ];
    }

    public createDateOperatorGroup(): FilterOperatorET[] {
        return [
            FilterOperatorET.EQUAL,
            FilterOperatorET.NOT_EQUAL,
            FilterOperatorET.GREATER_THAN,
            FilterOperatorET.GREATER_OR_EQUAL,
            FilterOperatorET.LESS_THAN,
            FilterOperatorET.LESS_OR_EQUAL
        ];
    }

    public createEnumOperatorGroup(): FilterOperatorET[] {
        return [
            FilterOperatorET.EQUAL,
            FilterOperatorET.NOT_EQUAL
        ];
    }
}