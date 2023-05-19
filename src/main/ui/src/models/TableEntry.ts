import type {GenericEntity} from "@/models/runtime/GenericEntity";
import type {Action} from "@/models/Action";

export interface TableEntry {
    entity: GenericEntity;
    actions?: Array<Action>;
    color?: string;
    [key: string]: any;
}