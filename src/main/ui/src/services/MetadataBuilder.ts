import {isPDAssociationMeta} from "@/models/core/PDAssociationMeta";
import {MetadataBuilderError} from "@/models/errors/MetadataBuilderError";
import {ObjectStringifier} from "@/models/core/ObjectStringifier";
import {MetadataMemberNotFoundError} from "@/models/errors/MetadataMemberNotFoundError";
import {AssociationCardinalityET} from "@/models/runtime/AssociationCardinalityET";
import type {PDApplication} from "@/models/core/PDApplication";
import type {PDSubsystem} from "@/models/core/PDSubsystem";
import type {PDTypeMeta} from "@/models/core/PDTypeMeta";
import type {PDEnumMeta} from "@/models/core/PDEnumMeta";
import type {PDDetailViewMeta} from "@/models/view/PDDetailViewMeta";
import type {PDMeta} from "@/models/core/PDMeta";
import type {PDListViewMeta} from "@/models/view/PDListViewMeta";
import type {PDColumn} from "@/models/view/PDColumn";
import type {PDAttributeMeta} from "@/models/core/PDAttributeMeta";
import type {PDAssociationMeta} from "@/models/core/PDAssociationMeta";
import type {PDDetailViewGroupMeta} from "@/models/view/PDDetailViewGroupMeta";
import type {PDDetailViewGroupItemMeta} from "@/models/view/PDDetailViewGroupItemMeta";
import type {PDMemberMeta} from "@/models/core/PDMemberMeta";
import type {PDOperationMeta} from "@/models/core/PDOperationMeta";
import type {
    PDDetailViewGroupToManyAssociationItemMeta
} from "@/models/view/PDDetailViewGroupToManyAssociationItemMeta";

/**
 * Builder to build metadata object by a given json.
 * @author Kevin Link, Niklas Grau
 */
export class MetadataBuilder {
    private application?: PDApplication;
    private enumMetas: PDEnumMeta[];
    private typeMetas: PDTypeMeta[]
    private navigation: PDTypeMeta[]
    private listViewMetas: PDListViewMeta[]
    private detailViewMetas: PDDetailViewMeta[]
    private subsystems: PDSubsystem[]


    constructor() {
        this.enumMetas = [];
        this.typeMetas = [];
        this.navigation = [];
        this.subsystems = [];
        this.listViewMetas = [];
        this.detailViewMetas = [];
    }

    public buildApplication(metadata: PDApplication): PDApplication {
        this.enumMetas = this.buildEnumerations(metadata.enums);
        this.typeMetas = this.buildTypes(metadata.types);
        this.navigation = this.buildNavigation(metadata.navigation);
        this.listViewMetas = this.buildListViews(metadata.listViews);
        this.detailViewMetas = this.buildDetailViews(metadata.detailViews);
        this.subsystems = this.buildSubsystems(metadata.subsystems);

        this.application = {
            name: metadata.name,
            label: metadata.label,
            enums: this.enumMetas,
            types: this.typeMetas,
            navigation: this.navigation,
            subsystems: this.subsystems,
            listViews: this.listViewMetas,
            detailViews: this.detailViewMetas,
        };

        return this.application;
    }

    private buildEnumerations(object: any): PDEnumMeta[] {
        const enumMetas: PDEnumMeta[] = [];

        object.forEach((enumMeta: PDEnumMeta) => enumMetas.push(enumMeta));

        return enumMetas;
    }

    private buildTypes(object: any): PDTypeMeta[] {
        const typeMetas: PDTypeMeta[] = [];

        object.forEach((typeMeta: PDTypeMeta) => {
            typeMeta.attributes.forEach((attribute: PDAttributeMeta) => {
                if (attribute.values) {
                    const enumMeta: PDEnumMeta | undefined = this.enumMetas.find(
                        (enumMeta: PDEnumMeta) => enumMeta.name === String(attribute.values)
                    );

                    if (!enumMeta) {
                        throw new MetadataBuilderError(`EnumMeta "${attribute.values}" not found.`);
                    }

                    attribute.values = enumMeta;
                }
            });

            typeMeta.objectStringifier = new ObjectStringifier(String(typeMeta.objectStringifier));
            typeMetas.push(typeMeta);
        });

        return typeMetas;
    }

    private buildNavigation(object: any): PDTypeMeta[] {
        const navigation: PDTypeMeta[] = [];

        object.forEach((navigationItem: PDMeta) => {
            const typeMeta: PDTypeMeta | undefined = this.typeMetas.find(
                (typeMeta: PDTypeMeta) => typeMeta.name === navigationItem.name
            );

            if (!typeMeta) {
                throw new MetadataBuilderError(`TypeMeta "${navigationItem.name}" not found.`);
            }

            navigation.push(typeMeta)
        });

        return navigation;
    }

    private buildListViews(object: any): PDListViewMeta[] {
        const listViewMetas: PDListViewMeta[] = [];

        object.forEach((listViewMeta: PDListViewMeta) => {
            const typeMeta: PDTypeMeta | undefined = this.typeMetas.find(
                (typeMeta: PDTypeMeta) => typeMeta.name === listViewMeta.name
            );

            if (!typeMeta) {
                throw new MetadataBuilderError(`TypeMeta "${listViewMeta.name}" not found.`);
            }

            // Label fall-back
            listViewMeta.label = listViewMeta.label ?? typeMeta.labelPlural;

            listViewMeta.columns.forEach((column: PDColumn) => {
                const memberMeta = MetadataBuilder.getPDMemberMetaByName(column.name, typeMeta);
                if (!memberMeta) {
                    throw new MetadataMemberNotFoundError(typeMeta.name, column.name);
                }

                column.member = memberMeta;
                column.listView = listViewMeta;
            });

            listViewMeta.type = typeMeta;
            listViewMetas.push(listViewMeta)
        });

        return listViewMetas;
    }

    private buildDetailViews(object: any): PDDetailViewMeta[] {
        const detailViewMetas: PDDetailViewMeta[] = [];

        object.forEach((detailViewMeta: PDDetailViewMeta) => {
            const typeMeta: PDTypeMeta | undefined = this.typeMetas.find(
                (typeMeta: PDTypeMeta) => typeMeta.name === detailViewMeta.name
            );

            if (!typeMeta) {
                throw new MetadataBuilderError(`TypeMeta "${detailViewMeta.name}" not found.`);
            }

            detailViewMeta.groups.forEach((groupMeta: PDDetailViewGroupMeta) => {
                this.buildGroups(typeMeta, groupMeta);
                this.buildItems(typeMeta, groupMeta);
            });

            // Label fall-back
            detailViewMeta.label = detailViewMeta.label ?? typeMeta.label;

            detailViewMeta.type = typeMeta;
            detailViewMetas.push(detailViewMeta)
        });

        return detailViewMetas;
    }

    public buildSubsystems(object: any): PDSubsystem[] {
        const subsystems: PDSubsystem[] = [];

        if (!object) {
            throw new MetadataBuilderError("Failed to build subsystems: Given data is undefined.");
        }

        object.forEach((subsystem: PDSubsystem) => {
            subsystems.push(subsystem)
        });

        return subsystems;
    }

    private buildGroups(typeMeta: PDTypeMeta, parentGroupMeta: PDDetailViewGroupMeta) {
        parentGroupMeta.groups?.forEach((groupMeta: PDDetailViewGroupMeta) => {
            if (typeMeta) {
                groupMeta.type = typeMeta;
            }
            groupMeta.parent = groupMeta;
            this.buildGroups(typeMeta, groupMeta);

        });
        this.buildItems(typeMeta, parentGroupMeta);
    }

    private buildItems(typeMeta: PDTypeMeta, parentGroupMeta: PDDetailViewGroupMeta) {
        parentGroupMeta.items?.forEach((itemMeta: PDDetailViewGroupItemMeta) => {
                if (typeMeta) {
                    const memberMeta = MetadataBuilder.getPDMemberMetaByName(itemMeta.name, typeMeta);

                    if (memberMeta) {
                        itemMeta.member = memberMeta;
                        itemMeta.label = memberMeta.label;

                        if (isPDAssociationMeta(memberMeta)) {
                            const memberTypeMeta = this.typeMetas.find((type: PDTypeMeta) => type.name === memberMeta.type);
                            if (!memberTypeMeta) {
                                throw new MetadataBuilderError(`Could not find any type for association member "${memberMeta.name}" of type ${typeMeta.name}.`);
                            }

                            if (memberMeta.cardinality === AssociationCardinalityET.MANY) {
                                const listViewMeta = this.listViewMetas.find((listView: PDListViewMeta) => listView.name === memberTypeMeta.name);
                                if (!listViewMeta) {
                                    throw new MetadataMemberNotFoundError(typeMeta.name, itemMeta.name);
                                }
                                (<PDDetailViewGroupToManyAssociationItemMeta>itemMeta).listView = listViewMeta;
                            }
                            itemMeta.type = memberTypeMeta;
                        } else {
                            itemMeta.type = typeMeta;
                        }
                    } else {
                        throw new MetadataMemberNotFoundError(typeMeta.name, itemMeta.name);
                    }
                }
            }
        );
    }

    private static getPDMemberMetaByName(memberName: string, typeMeta?: PDTypeMeta): PDMemberMeta | undefined {
        if (!typeMeta) {
            throw new MetadataBuilderError(`PDTypeMeta is not present while searching for PDMemberMeta with name '${memberName}'.`);
        }

        let attrMeta: PDAttributeMeta | undefined = typeMeta.attributes.find((attribute: PDAttributeMeta) => attribute.name === memberName);
        if (attrMeta) return attrMeta;

        let associationMeta: PDAssociationMeta | undefined = typeMeta.associations?.find((association: PDAssociationMeta) => association.name === memberName);
        if (associationMeta) return associationMeta;

        return typeMeta.operations?.find((operation: PDOperationMeta) => operation.name === memberName);
    }
}