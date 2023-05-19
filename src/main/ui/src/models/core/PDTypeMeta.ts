import type {PDMeta} from "@/models/core/PDMeta";
import type {PDAttributeMeta} from "@/models/core/PDAttributeMeta";
import type {PDOperationMeta} from "@/models/core/PDOperationMeta";
import type {PDAssociationMeta} from "@/models/core/PDAssociationMeta";
import type {MultiLanguageLabel} from "@/models/core/MultiLanguageLabel";
import type {ObjectStringifier} from "@/models/core/ObjectStringifier";

export interface PDTypeMeta extends PDMeta {
    typeId: Number;
    labelPlural: MultiLanguageLabel;
    icon?: string;
    objectStringifier: ObjectStringifier;
    subsystem?: string;
    base?: PDTypeMeta;
    attributes: PDAttributeMeta[];
    associations?: PDAssociationMeta[];
    operations?: PDOperationMeta[];
}
