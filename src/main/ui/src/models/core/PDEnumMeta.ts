import type {PDMeta} from "@/models/core/PDMeta";

export interface PDEnumMeta extends PDMeta {
    values: PDMeta[];
}
