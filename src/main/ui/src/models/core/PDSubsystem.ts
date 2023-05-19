import type {PDMeta} from "@/models/core/PDMeta";

export interface PDSubsystem extends PDMeta {
    parent?: PDSubsystem;
    children?: PDSubsystem[];
    icon?: string;
}
