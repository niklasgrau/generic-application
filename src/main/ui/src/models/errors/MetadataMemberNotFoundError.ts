import {BaseError} from "@/models/errors/BaseError";

export class MetadataMemberNotFoundError extends BaseError {
    constructor(typeName: string, memberName: string) {
        super(`Metadata does not contain the member "${memberName}" in the type "${typeName}".`, 102);
    }
}