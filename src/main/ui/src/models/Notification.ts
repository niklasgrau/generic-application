import type {NotificationTypeET} from "@/models/NotificationTypeET";

export class Notification {
    private _type: NotificationTypeET;
    private _message: string;

    constructor(type: NotificationTypeET, message: string) {
        this._type = type;
        this._message = message;
    }

    get type(): NotificationTypeET {
        return this._type;
    }

    set type(value: NotificationTypeET) {
        this._type = value;
    }

    get message(): string {
        return this._message;
    }

    set message(value: string) {
        this._message = value;
    }
}