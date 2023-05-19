import type {PDMeta} from "@/models/core/PDMeta";
import type {PDOperationMeta} from "@/models/core/PDOperationMeta";
import type {PDTypeMeta} from "@/models/core/PDTypeMeta";

export interface PDViewMeta extends PDMeta {
    type: PDTypeMeta;
    uiFactory?: string;
    uiFactoryParams?: {[key:string]: Map<string, string>};
    operations?: PDOperationMeta[];
}