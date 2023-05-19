import {ViewContext} from "@/models/runtime/ViewContext";
import {AssociationState} from "@/models/runtime/AssociationState";
import type {GenericEntity} from "@/models/runtime/GenericEntity";
import type {PDDetailViewMeta} from "@/models/view/PDDetailViewMeta";
import {AssociationCardinalityET} from "@/models/runtime/AssociationCardinalityET";

export class DetailViewContext extends ViewContext {
    private _detailViewMeta: PDDetailViewMeta;
    private _entity: GenericEntity;
    private _associationStates: Map<String, AssociationState>;
    private _files: Map<String, File>;

    constructor(detailViewMeta: PDDetailViewMeta) {
        super();

        this._detailViewMeta = detailViewMeta;
        this._associationStates = new Map<String, AssociationState>();
        this._files = new Map<String, File>();
    }

    private initAssociationStates() {
        this._associationStates = new Map<String, AssociationState>();
        this.detailViewMeta.type.associations.map(association => {
            if (association.cardinality === AssociationCardinalityET.MANY) {
                this._associationStates.set(association.name, new AssociationState(this.entity));
            }
        });
    }

    get detailViewMeta(): PDDetailViewMeta {
        return this._detailViewMeta;
    }

    set detailViewMeta(value: PDDetailViewMeta) {
        this._detailViewMeta = value;
    }

    get entity(): GenericEntity {
        return this._entity;
    }

    set entity(value: GenericEntity) {
        this._entity = value;
        this.initAssociationStates()
    }

    get associationStates(): Map<String, AssociationState> {
        return this._associationStates;
    }

    set associationStates(value: Map<String, AssociationState>) {
        this._associationStates = value;
    }

    get files(): Map<String, File> {
        return this._files;
    }

    set files(value: Map<String, File>) {
        this._files = value;
    }

    setFile(name: string, file: File) {
        this._files.set(name, file);
    }
}
