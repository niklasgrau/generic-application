import {BaseError} from "@/models/errors/BaseError";

export class MetadataPropertyNotFoundError extends BaseError {
    constructor(propertyName: String) {
        super(`Metadata does not contain the property "${propertyName}".`, 101);
    }
}