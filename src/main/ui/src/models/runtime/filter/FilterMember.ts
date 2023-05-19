import type {FilterOperatorET} from "@/models/runtime/filter/FilterOperatorET";

export interface FilterMember {
    memberName: String | null;
    operator: FilterOperatorET | String | null;
    value: Object | null;
}