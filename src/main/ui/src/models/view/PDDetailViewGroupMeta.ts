import type {PDUIElementMeta} from "@/models/view/PDUIElementMeta";
import type {PDDetailViewGroupItemMeta} from "@/models/view/PDDetailViewGroupItemMeta";

export interface PDDetailViewGroupMeta extends PDUIElementMeta {
    parent?: PDDetailViewGroupMeta;
    groups?: PDDetailViewGroupMeta[];
    items?: PDDetailViewGroupItemMeta[];
}