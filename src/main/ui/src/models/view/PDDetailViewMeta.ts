import type {PDViewMeta} from "@/models/view/PDViewMeta";
import type {PDDetailViewGroupMeta} from "@/models/view/PDDetailViewGroupMeta";

export interface PDDetailViewMeta extends PDViewMeta {
    groups: PDDetailViewGroupMeta[];
}