import {BaseError} from "@/models/errors/BaseError";

export class MetadataTypeNotFoundError extends BaseError {
    constructor(typeName: string) {
        super(`Metadata does not contain the type "${typeName}".`, 103);
    }
}