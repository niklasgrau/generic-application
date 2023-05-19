import type {MultiLanguageLabel} from "@/models/core/MultiLanguageLabel";

export interface PDValidatorMeta {
    errorMessage: MultiLanguageLabel;
    expression: string;
}
