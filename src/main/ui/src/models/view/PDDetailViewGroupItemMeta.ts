import type {PDUIElementMeta} from "@/models/view/PDUIElementMeta";
import type {PDMemberMeta} from "@/models/core/PDMemberMeta";

export interface PDDetailViewGroupItemMeta extends PDUIElementMeta {
    member: PDMemberMeta;
    disabled: boolean;
}