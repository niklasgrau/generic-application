import type {GenericEntity} from "@/models/runtime/GenericEntity";
import type {AssociationState} from "@/models/runtime/AssociationState";
import {Service} from "@/services/Service";
import type {FilterStatement} from "@/models/runtime/filter/FilterStatement";

/**
 * Service to manage api calls for metadata.
 * @author Niklas Grau
 */
export class GenericService extends Service {

    public static create(entity: GenericEntity, associationStates: Map<String, AssociationState>): Promise<any> {
        return fetch(`/api/generic`, {
            method: "POST",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                entity: entity,
                associationStates: associationStates && associationStates.size > 0 ? Object.fromEntries(associationStates) : null
            }).replace(/"_/g, '"')
        })
            .then(this.handleErrors)
            .then((response) => response.json());
    }

    public static fetchAll(typeId: Number, properties: Array<String>, page: Number, pageSize: Number, sorting: Array<Object>, filters: FilterStatement): Promise<any> {
        return fetch(`/api/generic/list?typeId=${typeId}`, {
            method: "POST",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                typeId: typeId,
                propertySelection: properties,
                page: page,
                pageSize: pageSize,
                sort: {
                    items: sorting
                },
                filter: filters,
            })
        })
            .then(this.handleErrors)
            .then((response) => response.json());
    }

    public static fetch(typeId: number, id: number, properties: Array<String>): Promise<any> {
        return fetch(`/api/generic/${id}?typeId=${typeId}`, {
            method: "POST",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                typeId: typeId,
                id: id,
                propertySelection: properties,
            })
        })
            .then(this.handleErrors)
            .then((response) => response.json());
    }

    public static update(entity: GenericEntity, associationStates: Map<String, AssociationState>): Promise<any> {
        return fetch(`/api/generic/${entity.id}`, {
            method: "PUT",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            },

            body: JSON.stringify({
                id: entity.id,
                entity: entity,
                associationStates: Object.fromEntries(associationStates)
            }).replace(/"_/g, '"')
        })
            .then(this.handleErrors)
            .then((response) => response.json());
    }

    public static delete(entity: GenericEntity): Promise<any> {
        return fetch(`/api/generic/${entity.id}?typeId=${entity.typeId}`, {
            method: "DELETE"
        })
            .then(this.handleErrors)
            .then(value => true);
    }
}