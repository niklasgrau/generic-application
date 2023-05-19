import type {PDViewMeta} from "@/models/view/PDViewMeta";
import type {PDColumn} from "@/models/view/PDColumn";

export interface PDListViewMeta extends PDViewMeta {
    columns: PDColumn[];
}