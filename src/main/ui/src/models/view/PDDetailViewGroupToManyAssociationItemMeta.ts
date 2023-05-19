import type {PDDetailViewGroupItemMeta} from "@/models/view/PDDetailViewGroupItemMeta";
import type {PDListViewMeta} from "@/models/view/PDListViewMeta";

export interface PDDetailViewGroupToManyAssociationItemMeta extends PDDetailViewGroupItemMeta {
    listView: PDListViewMeta;
}