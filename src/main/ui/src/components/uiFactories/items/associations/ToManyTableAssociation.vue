<template>
  <column-component :context="context">
    <v-row v-if="uiFactory.showFilter === 'true'">
      <v-col>
        <filter-component v-model="filters" :context="context.listView" @filter="fetchAll"/>
      </v-col>
    </v-row>
    <v-row>
      <v-col>
        <v-card :loading="isLoading">
          <v-card-title>
            <v-row justify="space-between">
              <v-col v-text="context.member.label?.labels[currentLocale] + ` (${paging.totalEntries})`"/>
              <v-col class="text-right">
                <v-menu v-model="menu" :close-on-content-click="false" location="bottom">
                  <template v-slot:activator="{ props }">
                    <v-btn color="primary" v-bind="props">
                      {{ $t('actions.link', {entity: context.type.label.labels[currentLocale]}) }}
                    </v-btn>
                  </template>

                  <v-card min-width="300">
                    <autocomplete-association v-model="association" :context="context" :entity="entity"
                                              :detail-view-context="detailViewContext"/>
                    <v-card-actions>
                      <v-spacer></v-spacer>
                      <v-btn variant="text" @click="menu = !menu">
                        {{ $t('actions.cancel') }}
                      </v-btn>
                      <v-btn color="primary" variant="text" @click="connect(association)">
                        {{ $t('actions.linkSimple') }}
                      </v-btn>
                    </v-card-actions>
                  </v-card>
                </v-menu>
              </v-col>
            </v-row>
          </v-card-title>
          <v-row v-if="!contextChanged">
            <v-col cols="12">
              <table-component :columns="columns"
                               :actions="actions"
                               :entries="entries"
                               :sorting="sorting"
                               @sort="fetchAll()"/>
            </v-col>
          </v-row>
        </v-card>
      </v-col>
    </v-row>

    <v-row class="pt-6">
      <v-col cols="12">
        <paging-component v-model:page="paging.page"
                          v-model:page-size="paging.pageSize"
                          :total-pages="paging.totalPages"
                          @change="fetchAll"/>
      </v-col>
    </v-row>
  </column-component>
</template>

<script lang="ts">
import {defineComponent, type PropType} from "vue";
import {useApplicationStore} from "@/stores/application";
import {useRoute} from "vue-router";
import PagingComponent from "@/components/PagingComponent.vue";
import FilterComponent from "@/components/FilterComponent.vue";
import TableComponent from "@/components/TableComponent.vue";
import AbstractItemComponent from "@/components/uiFactories/items/AbstractItemComponent.vue";
import AutocompleteAssociation from "@/components/uiFactories/items/associations/AutocompleteAssociation.vue";
import {GenericService} from "@/services/GenericService";
import type {
  PDDetailViewGroupToManyAssociationItemMeta
} from "@/models/view/PDDetailViewGroupToManyAssociationItemMeta";
import {FilterBlockOperatorET} from "@/models/runtime/filter/FilterBlockOperatorET";
import {FilterOperatorET} from "@/models/runtime/filter/FilterOperatorET";
import {AssociationStateTypeET} from "@/models/AssociationStateTypeET";
import type {PDTypeMeta} from "@/models/core/PDTypeMeta";
import type {PDColumn} from "@/models/view/PDColumn";
import type {GenericEntity} from "@/models/runtime/GenericEntity";
import type {Column} from "@/models/Column";
import type {SortItem} from "@/models/SortItem";
import type {Action} from "@/models/Action";
import type {FilterBlock} from "@/models/runtime/filter/FilterBlock";
import type {PDAssociationMeta} from "@/models/core/PDAssociationMeta";
import type {AssociationState} from "@/models/runtime/AssociationState";
import type {TableEntry} from "@/models/TableEntry";
import type {FilterStatement} from "@/models/runtime/filter/FilterStatement";

/**
 * Displays a list view like table with filter for to-many associated entities.
 * @author Kevin Link
 *
 * @param showFilter Boolean value to show or hide the integrated filter component.
 */
export default defineComponent({
  name: "ToManyTableAssociation",
  extends: AbstractItemComponent,
  components: {
    AutocompleteAssociation,
    TableComponent,
    FilterComponent,
    PagingComponent
  },
  setup() {
    const applicationStore = useApplicationStore();
    const route = useRoute();

    return {
      applicationStore,
      route
    }
  },
  props: {
    context: {
      type: Object as PropType<PDDetailViewGroupToManyAssociationItemMeta>,
      required: true
    }
  },
  data() {
    return {
      contextChanged: false,
      isLoading: false,
      currentType: null as PDTypeMeta | null,
      entries: [] as Array<TableEntry>,
      columns: [] as Array<Column>,
      actions: [
        {
          name: "edit",
          icon: "mdi-pencil",
          callback: (entry: TableEntry) => {
            this.$router.push({name: this.context.type.name, params: {id: entry.entity.id}});
          }
        },
        {
          name: "disconnect",
          icon: "mdi-link-off",
          callback: this.disconnect,
        },
        {
          name: "delete",
          icon: "mdi-delete",
          callback: this.delete
        }
      ] as Array<Action>,
      paging: {
        page: 1,
        pageSize: 15,
        totalPages: 1,
        totalEntries: 0
      },
      sorting: [] as Array<SortItem>,
      filters: [] as Array<FilterBlock>,
      defaultFilter: [] as Array<FilterBlock>,
      connectedFilter: [] as Array<FilterBlock>,
      menu: false,
      association: null,
      associationChanges: {} as AssociationState,
      states: {
        connected: {
          color: "bg-green-lighten-5"
        },
        disconnected: {
          color: "bg-yellow-lighten-5"
        },
        deleted: {
          color: "bg-red-lighten-5"
        }
      },
      uiFactory: {
        showFilter: this.context.uiFactoryParams?.['showFilter'] ?? 'true'
      }
    }
  },
  methods: {
    fetchAll: function () {
      this.currentType = this.getType();

      if (this.currentType === null) {
        console.error("Failed to get type of list view.");
        return;
      }

      if (this.isLoading) {
        return;
      }
      this.isLoading = true;

      let propertySelection: Array<String> = this.getColumns().map(column => column.member.name);

      GenericService.fetchAll(
          this.currentType.typeId,
          propertySelection,
          this.paging.page,
          this.paging.pageSize,
          this.sorting,
          this.assembleFilter())
          .then((data) => {
            this.entries = [];
            data.content.forEach((entity: GenericEntity) => {
              this.entries.push(this.assembleEntry(entity));
            });
            this.paging.page = data.number + 1;
            this.paging.pageSize = data.size;
            this.paging.totalPages = data.totalPages;
            this.paging.totalEntries = data.totalElements;
          })
          .catch(error => console.error(error))
          .finally(() => this.buildListView());
    },
    assembleEntry: function (entity: GenericEntity) {
      const stateType = this.lookupAssociationState(entity);
      return {
        color: (stateType !== AssociationStateTypeET.ORIGIN) ? this.states[stateType].color : "",
        entity: entity,
        actions: (stateType !== AssociationStateTypeET.ORIGIN) ? [{
          name: "undo",
          icon: "mdi-undo",
          callback: this.undoChange
        }] : [],
        associationType: stateType
      }
    },
    getType: function (): PDTypeMeta | null {
      return this.context.listView.type;
    },
    getColumns: function (): Array<PDColumn> {
      return this.context.listView.columns;
    },
    buildListView: function () {
      this.buildColumns();
      this.isLoading = false;
      this.contextChanged = false;
    },
    buildColumns: function () {
      this.columns = [];
      for (const column of this.getColumns()) {
        const member = column.member;

        this.columns.push({
          name: member.name,
          label: member.label,
          sortable: column.sortable,
          searchable: column.searchable,
          width: column.width,
          formatterFactory: column.formatterFactory,
          formatterFactoryParams: column.formatterFactoryParams,
          meta: column
        });
      }
    },
    assembleFilter: function (): FilterStatement {
      return <FilterStatement>{
        blocks: <Array<FilterBlock>>[...this.filters, ...this.defaultFilter, ...this.connectedFilter],
      };
    },
    lookupAssociationState: function (entity: GenericEntity): AssociationStateTypeET {
      if (this.checkIfAssociationAlreadyChanged(entity, AssociationStateTypeET.CONNECTED)) {
        return AssociationStateTypeET.CONNECTED;
      } else if (this.checkIfAssociationAlreadyChanged(entity, AssociationStateTypeET.DISCONNECTED)) {
        return AssociationStateTypeET.DISCONNECTED;
      } else if (this.checkIfAssociationAlreadyChanged(entity, AssociationStateTypeET.DELETED)) {
        return AssociationStateTypeET.DELETED;
      }
      return AssociationStateTypeET.ORIGIN;
    },
    checkIfAssociationAlreadyChanged: function (association: GenericEntity, associationStateType: AssociationStateTypeET): boolean {
      const result = (<Array<GenericEntity>>this.associationChanges[associationStateType])
          .find((entity: GenericEntity) => entity.id === association.id && entity.typeId === association.typeId);
      return result?.id === association.id;
    },
    connect: function (association: GenericEntity) {
      if (!this.checkIfAssociationAlreadyChanged(association, AssociationStateTypeET.CONNECTED)) {
        this.associationChanges?.connected.push(association);
      }

      this.assembleConnectedFilter();

      this.association = null;
      this.menu = false;
    },
    disconnect: function (entry: TableEntry) {
      if (!this.checkIfAssociationAlreadyChanged(entry.entity, AssociationStateTypeET.DISCONNECTED)) {
        this.associationChanges?.disconnected.push(entry.entity);
      }
    },
    delete: function (entry: TableEntry) {
      if (!this.checkIfAssociationAlreadyChanged(entry.entity, AssociationStateTypeET.DELETED)) {
        this.associationChanges?.deleted.push(entry.entity);
      }
    },
    undoChange: function (entry: TableEntry) {
      const indexOfAssociation = this.associationChanges[entry.associationType]
          .findIndex((entity: GenericEntity) => entity.id === entry.entity.id && entity.typeId === entry.entity.typeId);
      this.associationChanges[entry.associationType].splice(indexOfAssociation, 1);
    },
    assembleConnectedFilter: function () {
      const ids = String(this.associationChanges?.connected.map(value => value.id).join(","));

      if (ids === "") {
        this.connectedFilter = [];
      } else {
        this.connectedFilter = [
          {
            operator: FilterBlockOperatorET.OR,
            statements: [
              {
                memberName: "id",
                operator: "IN",
                value: ids
              }
            ]
          }
        ];
      }
    }
  },
  watch: {
    "paging.pageSize": function (newPageSize, oldPageSize) {
      this.fetchAll();
    },
    "paging.page": function (newPage, oldPage) {
      this.fetchAll();
    },
    context: function (newContext, oldContext) {
      this.contextChanged = true;
      this.entries = [];
      this.columns = [];
      this.sorting = [] as Array<SortItem>;
      this.filters = [] as Array<FilterBlock>;
      // this.actions = [];
      this.fetchAll();
    },
    "detailViewContext.associationStates": {
      handler() {
        this.associationChanges = this.detailViewContext?.associationStates.get(this.context.member.name);
        this.assembleConnectedFilter();
        this.fetchAll();
      },
      deep: true
    }
  },
  mounted() {
    this.defaultFilter = [
      {
        operator: FilterBlockOperatorET.AND,
        statements: [
          {
            memberName: (<PDAssociationMeta>(this.context.member)).inverseOf,
            operator: FilterOperatorET.EQUAL,
            value: this.entity?.id
          }
        ]
      }
    ];

    this.associationChanges = this.detailViewContext?.associationStates.get(this.context.member.name);

    this.fetchAll();
  },
});
</script>