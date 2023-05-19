import {useApplicationStore} from "@/stores/application";

/**
 * Base service to define error handling.
 * @author Kevin Link
 */
export class Service {

    protected static async handleErrors(response: Response) {
        if (!response.ok) {
            const applicationStore = useApplicationStore();
            let text = await response.text()
            applicationStore.error(response.statusText + ": " + text);

            return Promise.reject(new Error(response.statusText + ": " + text));
        }
        return response;
    }
}