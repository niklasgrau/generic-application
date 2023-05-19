import type {PDTypeMeta} from "@/models/core/PDTypeMeta";

export class GenericEntity {
    private _typeId: Number;
    private _id: Number;
    private _properties: Object;
    private _objectStringifier: String;
    private _pdTypeMeta: PDTypeMeta;

    constructor(typeId: Number, id: Number, properties: Object, pdTypeMeta: PDTypeMeta) {
        this._typeId = typeId;
        this._id = id;
        this._properties = properties;
        this._pdTypeMeta = pdTypeMeta;

        this._objectStringifier = this.generateObjectStringifier();
    }

    private generateObjectStringifier(): string {
        const attributeNames = this._pdTypeMeta.objectStringifier.attributes;
        const attributes = new Map<string, any>();
        attributeNames.forEach(attributeName => attributes.set(attributeName, this._properties[attributeName as keyof Object]));
        return this._pdTypeMeta.objectStringifier.toString(attributes);
    }

    get typeId(): Number {
        return this._typeId;
    }

    set typeId(value: Number) {
        this._typeId = value;
    }

    get id(): Number {
        return this._id;
    }

    set id(value: Number) {
        this._id = value;
    }

    get properties(): Object {
        return this._properties;
    }

    set properties(value: Object) {
        this._properties = value;
    }

    get objectStringifier(): String {
        return this._objectStringifier;
    }

    set objectStringifier(value: String) {
        this._objectStringifier = value;
    }

    get pdTypeMeta(): PDTypeMeta {
        return this._pdTypeMeta;
    }

    set pdTypeMeta(value: PDTypeMeta) {
        this._pdTypeMeta = value;
    }
}
