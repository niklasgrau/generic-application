import type {PDListViewMeta} from "@/models/view/PDListViewMeta";
import type {PDDetailViewGroupItemMeta} from "@/models/view/PDDetailViewGroupItemMeta";

export interface PDColumn extends PDDetailViewGroupItemMeta {
    sortable: boolean;
    searchable: boolean;
    width: number;
    listView: PDListViewMeta;
    formatterFactory?: string;
    formatterFactoryParams?: {[key:string]: Map<string, string>};
}