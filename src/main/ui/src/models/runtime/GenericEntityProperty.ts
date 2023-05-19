export interface GenericEntityProperty {
    typeId: number;
    id: number;
}

export function isGenericEntityProperty(object: GenericEntityProperty) {
    return "typeId" in object
        && "id" in object;
}