import type {PDMemberMeta} from "@/models/core/PDMemberMeta";
import type {PDValidatorMeta} from "@/models/core/PDValidatorMeta";
import type {AssociationCardinalityET} from "@/models/runtime/AssociationCardinalityET";

export interface PDAssociationMeta extends PDMemberMeta {
    type: string;
    required: boolean;
    inverseOf: string;
    validators?: PDValidatorMeta[];
    cardinality?: AssociationCardinalityET;
}

export function isPDAssociationMeta(obj: any): obj is PDAssociationMeta {
    return 'required' in obj && 'type' in obj && 'inverseOf' in obj;
}
