import type {MultiLanguageLabel} from "@/models/core/MultiLanguageLabel";
import type {DataTypeET} from "@/models/core/DataTypeET";
import type {PDColumn} from "@/models/view/PDColumn";

export interface Column {
    name: string;
    label: MultiLanguageLabel;
    sortable: boolean;
    searchable: boolean;
    width: Number | undefined;
    type?: DataTypeET;
    uiFactory?: string;
    uiFactoryParams?: Map<string, string>;
    formatterFactory?: string;
    formatterFactoryParams?: Map<string, string>;
    meta?: PDColumn;
}