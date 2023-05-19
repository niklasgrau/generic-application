export class ObjectStringifier {
    private _objectStringifier: string;
    private _attributes: string[];

    readonly placeholdersRegex = /{{(\w*?)}}*/g;
    readonly attributesRegex = /(?<={{)\w*(?=}})/g;

    constructor(objectStringifier: string) {
        this._objectStringifier = objectStringifier;
        this._attributes = this.extractAttributes();
    }

    get objectStringifier(): string {
        return this._objectStringifier;
    }

    set objectStringifier(value: string) {
        this._objectStringifier = value;
    }

    get attributes(): string[] {
        return this._attributes;
    }

    set attributes(value: string[]) {
        this._attributes = value;
    }

    public toString(attributes: Map<string, any>): string {
        return this._objectStringifier.replace(this.placeholdersRegex, (match, key) => {
            return attributes.get(key) || match;
        })
    }

    public extractAttributes(): string[] {
        let attributes: string[] = [];
        let matches;
        while ((matches = this.attributesRegex.exec(this._objectStringifier)) !== null) {
            if (matches.index === this.attributesRegex.lastIndex) {
                this.attributesRegex.lastIndex++;
            }

            attributes.push(matches[0])
        }

        return attributes;
    }
}