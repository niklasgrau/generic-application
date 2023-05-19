import type {GenericEntity} from "@/models/runtime/GenericEntity";

export class AssociationState {
    public origin: GenericEntity;
    public connected: GenericEntity[];
    public disconnected: GenericEntity[];
    public deleted: GenericEntity[];

    constructor(origin: GenericEntity) {
        this.origin = origin;
        this.connected = [];
        this.disconnected = [];
        this.deleted = [];
    }

    public mergeChanges(): void {
        // First, mutually cancel connect or disconnect changes if entity will be deleted anyway
        const connectedDeletedDiff = this.diff(this.connected, this.deleted, "id");
        const disconnectedDeletedDiff = this.diff(this.disconnected, this.deleted, "id");

        // Second, mutually cancel connect/disconnect by the deleted diff of both
        const connectedDisconnectedDiff = this.diff(connectedDeletedDiff, disconnectedDeletedDiff, "id");
        const disconnectedConnectedDiff = this.diff(disconnectedDeletedDiff, connectedDeletedDiff, "id");

        // Update the values except deleted, since there is no created to diff with
        this.connected = connectedDisconnectedDiff;
        this.disconnected = disconnectedConnectedDiff;
    }

    private diff<T>(array1: T[], array2: T[], key: keyof T): T[] {
        const array2Set = new Set(array2.map((item) => item[key]));

        return array1.filter((item) => !array2Set.has(item[key]));
    }
}
