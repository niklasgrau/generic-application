import {Service} from "@/services/Service";

/**
 * Service to manage api calls for files.
 * @author Kevin Link
 */
export class FileService extends Service {
    public static upload(body: FormData): Promise<any> {
        return fetch(`/api/storage`, {
            method: "POST",
            body: body
        }).then(this.handleErrors);
    }

    public static get(typeId: number, id: number, propertyName: string): Promise<any> {
        return fetch(`/api/storage?typeId=${typeId}&id=${id}&propertyName=${propertyName}`, {
            method: "GET",
            headers: {},
        }).then(this.handleErrors);
    }

    public static delete(typeId: number, id: number, propertyName: string): Promise<any> {
        return fetch(`/api/storage?typeId=${typeId}&id=${id}&propertyName=${propertyName}`, {
            method: "DELETE",
            headers: {},
        }).then(this.handleErrors)
            .then(value => true);
    }

    /**
     * Creates a default form data object for the generic file handler.
     * @param typeId Unique identifier of a specific entity type.
     * @param id Primary ID of the entity.
     * @param propertyName Name of the file property of the entity.
     * @param file The File content to upload.
     */
    public static buildFilePayload(typeId: number, id: number, propertyName: string, file: File): FormData {
        let formData: FormData = new FormData()
        formData.append("typeId", String(typeId));
        formData.append("id", String(id));
        formData.append("propertyName", String(propertyName));
        formData.append("file", file);

        return formData;
    }
}