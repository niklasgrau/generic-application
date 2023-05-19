import type {PDTypeMeta} from "@/models/core/PDTypeMeta";
import type {PDMeta} from "@/models/core/PDMeta";
import type {PDListViewMeta} from "@/models/view/PDListViewMeta";
import type {PDDetailViewMeta} from "@/models/view/PDDetailViewMeta";
import type {PDSubsystem} from "@/models/core/PDSubsystem";
import type {PDEnumMeta} from "@/models/core/PDEnumMeta";

export interface PDApplication extends PDMeta {
    types: PDTypeMeta[]
    navigation: PDTypeMeta[]
    subsystems?: PDSubsystem[]
    listViews: PDListViewMeta[]
    detailViews: PDDetailViewMeta[]
    enums?: PDEnumMeta[]
}
