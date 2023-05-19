import type {FilterBlockOperatorET} from "@/models/runtime/filter/FilterBlockOperatorET";
import type {FilterMember} from "@/models/runtime/filter/FilterMember";


export interface FilterBlock {
    operator: FilterBlockOperatorET;
    statements: Array<FilterMember>;
}