import type {MultiLanguageLabel} from "@/models/core/MultiLanguageLabel";
import type {BreadcrumbItemTypeET} from "@/models/BreadcrumbItemTypeET";

export interface BreadcrumbItem {
    title: MultiLanguageLabel | string;
    disabled: boolean;
    to: string;
    type: BreadcrumbItemTypeET;
    contextTitle?: MultiLanguageLabel;
}