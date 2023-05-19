import type {UserContext} from "@/models/runtime/UserContext";
import type {PDApplication} from "@/models/core/PDApplication";

export class Application {
    private _hasAuthenticatedUser: boolean;
    private _currentUserContext: UserContext;
    private _applicationMeta: PDApplication;

    constructor(applicationMeta: PDApplication) {
        this._applicationMeta = applicationMeta;
    }

    get hasAuthenticatedUser(): boolean {
        return this._hasAuthenticatedUser;
    }

    set hasAuthenticatedUser(value: boolean) {
        this._hasAuthenticatedUser = value;
    }

    get currentUserContext(): UserContext {
        return this._currentUserContext;
    }

    set currentUserContext(value: UserContext) {
        this._currentUserContext = value;
    }

    get applicationMeta(): PDApplication {
        return this._applicationMeta;
    }

    set applicationMeta(value: PDApplication) {
        this._applicationMeta = value;
    }
}