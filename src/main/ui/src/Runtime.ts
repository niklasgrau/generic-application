import {useApplicationStore} from "@/stores/application";
import router from "@/router";
import TypeRegistryView from "@/views/TypeRegistryView.vue";
import ListView from "@/views/ListView.vue";
import DetailView from "@/views/DetailView.vue";
import {Application} from "@/models/runtime/Application";
import {MetadataBuilder} from "@/services/MetadataBuilder";
import {MetadataService} from "@/services/MetadataService";
import {BreadcrumbItemTypeET} from "@/models/BreadcrumbItemTypeET";
import {InitializationError} from "@/models/errors/InitializationError";
import {MetadataTypeNotFoundError} from "@/models/errors/MetadataTypeNotFoundError";
import type {PDApplication} from "@/models/core/PDApplication";
import type {PDTypeMeta} from "@/models/core/PDTypeMeta";
import type {PDDetailViewMeta} from "@/models/view/PDDetailViewMeta";
import type {PDListViewMeta} from "@/models/view/PDListViewMeta";
import type {BreadcrumbItem} from "@/models/BreadcrumbItem";
import type {MultiLanguageLabel} from "@/models/core/MultiLanguageLabel";
import type {Component} from "vue";
import type {PDViewMeta} from "@/models/view/PDViewMeta";
import type {PDSubsystem} from "@/models/core/PDSubsystem";

/**
 * Runtime to fetch and initialize metadata to generate routes.
 * @author Kevin Link
 */
export class Runtime {

    /**
     * Starts the initialisation of the runtime while fetching and parsing metadata.
     * The parsed metadata will be stored in ApplicationStore and used to generate routes.
     */
    public static async initialize(): Promise<void> {
        console.info("Initializing runtime ...")
        await MetadataService.fetchMetadata()
            .then(metadata => this.parseAndStoreMetadata(metadata))
            .then((value) => this.generateRoutes(value))
            .catch((error) => {
                throw new InitializationError("Initialization failed. " + error,);
            });
        console.info("Initialization finished.")
    }

    private static parseAndStoreMetadata(metadata: PDApplication): PDApplication | null {
        const appStore = useApplicationStore();
        const metadataBuilder = new MetadataBuilder();
        const applicationData = metadataBuilder.buildApplication(metadata)

        if (!applicationData) {
            throw new InitializationError("Application data could not be parsed.");
        }

        appStore.app = new Application(applicationData);

        return applicationData;
    }

    private static generateRoutes(applicationMeta: PDApplication | null): void {
        if (applicationMeta === null) {
            throw new InitializationError("No application meta data available, to generate routes from.");
        }

        const types = applicationMeta.types;

        // Type registry
        this.addTypeRegistryRoute(types);

        // Register ListView routes
        this.addListViewRoutes(applicationMeta);

        // Register DetailView routes
        this.addDetailViewRoutes(applicationMeta);

        console.debug("Generated routes:", router.getRoutes());
    }

    private static addTypeRegistryRoute(types: Array<PDTypeMeta>): void {
        router.addRoute({
            name: "type-registry",
            path: "/type-registry",
            component: TypeRegistryView,
            props: {
                types,
            },
            meta: {
                subsystem: null,
                breadcrumb: <Array<BreadcrumbItem>>[
                    {
                        title: "nav.typeRegistry",
                        disabled: false,
                        to: "/type-registry",
                        type: BreadcrumbItemTypeET.NONE
                    }
                ]
            }
        });
    }

    private static addListViewRoutes(applicationMeta: PDApplication): void {
        const types = applicationMeta.types;
        const listViewMetas: Array<PDListViewMeta> = applicationMeta.listViews;

        for (const listViewMeta of listViewMetas) {
            const listViewType = types.find(type => listViewMeta.name === type.name);

            if (!listViewType) {
                throw new MetadataTypeNotFoundError(listViewMeta.name);
            }

            const subsystem = MetadataService.getSubsystemFromApplication(listViewType.subsystem, applicationMeta);

            const path: string = subsystem ? `/${subsystem.name}/${listViewType.name}s` : `/${listViewType.name}s`;
            const breadcrumb: Array<BreadcrumbItem> = [];

            if (subsystem) {
                breadcrumb.push({
                    title: subsystem.label,
                    disabled: true,
                    to: "",
                    type: BreadcrumbItemTypeET.SUBSYSTEM
                });
            }

            router.addRoute(this.buildRoute(`${listViewType.name}s`, path, ListView, listViewMeta,
                subsystem, breadcrumb, BreadcrumbItemTypeET.LIST_VIEW, listViewMeta.label, listViewType.labelPlural));
        }
    }

    private static addDetailViewRoutes(applicationMeta: PDApplication): void {
        const types = applicationMeta.types;
        const detailViewMetas: Array<PDDetailViewMeta> = applicationMeta.detailViews;

        for (const detailViewMeta of detailViewMetas) {
            const detailViewType = types.find(type => detailViewMeta.name === type.name);
            const listViewMeta = applicationMeta.listViews.find(listView => detailViewMeta.name === listView.name);

            if (!detailViewType) {
                throw new MetadataTypeNotFoundError(detailViewMeta.name);
            }

            const subsystem = MetadataService.getSubsystemFromApplication(detailViewType.subsystem, applicationMeta);

            const pathToDetailView = subsystem ? `/${subsystem.name}/${detailViewType.name}s/:id` : `/${detailViewType.name}s/:id`;
            const breadcrumb: Array<BreadcrumbItem> = [];

            if (subsystem) {
                breadcrumb.push({
                    title: subsystem.label,
                    disabled: true,
                    to: "",
                    type: BreadcrumbItemTypeET.SUBSYSTEM
                });
            }

            // Get list view breadcrumb if available
            if (listViewMeta) {
                const listViewRoute = router.getRoutes().find(route => route.name === `${detailViewType.name}s`);
                const listViewCrumb = (<Array<BreadcrumbItem>>listViewRoute?.meta.breadcrumb)
                    .find((breadcrumb: BreadcrumbItem) => BreadcrumbItemTypeET.LIST_VIEW === breadcrumb.type);
                if (listViewCrumb) {
                    breadcrumb.push(listViewCrumb);
                }
            }

            router.addRoute(this.buildRoute(`${detailViewType.name}`, pathToDetailView, DetailView, detailViewMeta,
                subsystem, breadcrumb, BreadcrumbItemTypeET.DETAIL_VIEW, detailViewMeta.label, listViewMeta?.label, detailViewType.labelPlural));

            const pathToCreateView = subsystem ? `/${subsystem.name}/${detailViewType.name}s/create` : `/${detailViewType.name}s/create`;
            router.addRoute({
                name: `create-${detailViewType.name}`,
                path: pathToCreateView,
                component: DetailView,
                props: {
                    context: detailViewMeta,
                    creationMode: true
                },
                meta: {
                    subsystem: subsystem,
                    breadcrumb: <Array<BreadcrumbItem>>[
                        ...breadcrumb,
                        {
                            title: "actions.create",
                            disabled: false,
                            to: pathToCreateView,
                            type: BreadcrumbItemTypeET.CREATE_VIEW,
                            contextTitle: detailViewType.label
                        }
                    ]
                }
            });
        }
    }

    /**
     * Builds and returns a route object.
     * @param routeName Name of the route
     * @param routePath Path of the route
     * @param component Component to be shown by the given route
     * @param context Context of the component
     * @param subsystem Subsystem of the route
     * @param breadcrumb Breadcrumb object for context of the route
     * @param breadcrumbType Type of the breadcrumb of this route
     * @param breadcrumbTitles Possible titles for the breadcrumb item of this route
     * @private
     */
    private static buildRoute(routeName: string, routePath: string, component: Component, context: PDViewMeta,
                              subsystem: PDSubsystem | null, breadcrumb: Array<BreadcrumbItem>,
                              breadcrumbType: BreadcrumbItemTypeET, ...breadcrumbTitles: Array<MultiLanguageLabel | undefined>) {
        return {
            name: routeName,
            path: routePath,
            component: component,
            props: {
                context: context
            },
            meta: {
                subsystem: subsystem,
                breadcrumb: <Array<BreadcrumbItem>>[
                    ...breadcrumb,
                    {
                        title: this.labelOf(...breadcrumbTitles),
                        disabled: false,
                        to: routePath,
                        type: breadcrumbType
                    }
                ]
            }
        };
    }

    /**
     * Returns the first not empty label or null if no label is available.
     * @param labels Labels to check
     * @private
     */
    private static labelOf(...labels: Array<MultiLanguageLabel | undefined>): MultiLanguageLabel | null {
        for (const label of labels) {
            if (label && Object.keys(label.labels).length > 0) {
                return label;
            }
        }
        return null;
    }
}

export default Runtime;