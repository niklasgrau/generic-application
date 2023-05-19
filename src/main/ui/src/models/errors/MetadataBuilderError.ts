import {BaseError} from "@/models/errors/BaseError";

export class MetadataBuilderError extends BaseError {
    constructor(message: string) {
        super(message, 100);
    }
}