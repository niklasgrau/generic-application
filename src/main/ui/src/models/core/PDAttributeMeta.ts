import type {PDMemberMeta} from "@/models/core/PDMemberMeta";
import type {PDValidatorMeta} from "@/models/core/PDValidatorMeta";
import type {PDEnumMeta} from "@/models/core/PDEnumMeta";
import type {DataTypeET} from "@/models/core/DataTypeET";

export interface PDAttributeMeta extends PDMemberMeta {
    required: boolean;
    dataType: DataTypeET;
    validators?: PDValidatorMeta[];
    values?: PDEnumMeta;
}

export function isPDAttributeMeta(obj: any): obj is PDAttributeMeta {
    return 'required' in obj && 'dataType' in obj;
}
