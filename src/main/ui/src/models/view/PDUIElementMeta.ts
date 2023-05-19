import type {PDViewMeta} from "@/models/view/PDViewMeta";

export interface PDUIElementMeta extends PDViewMeta {
    labelVisible: boolean;
    colLG: number;
    colMD?: number;
    colSM?: number;
    colOffset?: number;
    unwrapColumn?: boolean;
}