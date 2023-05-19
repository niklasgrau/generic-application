import type {PDApplication} from "@/models/core/PDApplication";
import type {PDSubsystem} from "@/models/core/PDSubsystem";

/**
 * Service to manage api calls for metadata.
 * @author Kevin Link
 */
export class MetadataService {
    public static fetchMetadata(): Promise<PDApplication> {
        return fetch("/api/metadata")
            .then((response) => response.json());
    }

    public static getSubsystemFromApplication(name: String | undefined, app: PDApplication): PDSubsystem | null {
        if (!name) {
            return null;
        }

        if (app === null) {
            console.error(`No application context given to find subsystem by name '${name}'.`);
            return null;
        }

        return app.subsystems?.filter(subsystem => subsystem.name === name)[0] ?? null;
    }

}