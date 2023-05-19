import type {PDMemberMeta} from "@/models/core/PDMemberMeta";
import type {PDAttributeMeta} from "@/models/core/PDAttributeMeta";

export interface PDOperationMeta extends PDMemberMeta {
    icon?: string;
}


export function isPDOperationMeta(obj: any): obj is PDOperationMeta {
    return 'icon' in obj;
}
