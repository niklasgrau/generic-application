export class BaseError extends Error {
    protected _code: number;

    constructor(message: string, code: number) {
        super(message);
        this._code = code;
    }

    get code(): number {
        return this._code;
    }
}
