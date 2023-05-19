import {BaseError} from "@/models/errors/BaseError";

export class InitializationError extends BaseError {
    constructor(message: string) {
        super(message, 1);
    }
}