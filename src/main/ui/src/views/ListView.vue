<template>
  <filter-component v-model="filters" :context="context" class="pb-6" @filter="fetchAll"/>
  <v-card :loading="isLoading">
    <v-card-title>
      <v-row justify="space-between">
        <v-col v-text="context.label?.labels[currentLocale] + ` (${paging.totalEntries})`"/>
        <v-col class="text-right">
          <v-btn prepend-icon="mdi-plus" variant="outlined" @click="navigateToCreate">
            {{ $t('actions.create', {entity: context.type.label?.labels[currentLocale]}) }}
          </v-btn>
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

  <v-row class="pt-6">
    <v-col cols="12">
      <paging-component v-model:page="paging.page"
                        v-model:page-size="paging.pageSize"
                        :total-pages="paging.totalPages"
                        @change="fetchAll"/>
    </v-col>
  </v-row>
</template>

<script lang="ts">
import {defineComponent, type PropType} from "vue";
import {useApplicationStore} from "@/stores/application";
import PagingComponent from "@/components/PagingComponent.vue";
import FilterComponent from "@/components/FilterComponent.vue";
import TableComponent from "@/components/TableComponent.vue";
import {GenericService} from "@/services/GenericService";
import type {PDListViewMeta} from "@/models/view/PDListViewMeta";
import type {PDColumn} from "@/models/view/PDColumn";
import type {Column} from "@/models/Column";
import type {SortItem} from "@/models/SortItem";
import type {Action} from "@/models/Action";
import type {FilterBlock} from "@/models/runtime/filter/FilterBlock";
import type {TableEntry} from "@/models/TableEntry";
import type {FilterStatement} from "@/models/runtime/filter/FilterStatement";

/**
 * Implementation of the generic list view.
 * @author Kevin Link
 */
export default defineComponent({
  name: "ListView",
  inject: ["currentLocale"],
  components: {TableComponent, FilterComponent, PagingComponent},
  setup() {
    const applicationStore = useApplicationStore();

    return {
      successMessage: applicationStore.success
    }
  },
  props: {
    context: {
      type: Object as PropType<PDListViewMeta>,
      required: true
    }
  },
  data: function () {
    return {
      contextChanged: false,
      isLoading: false,
      entries: [] as Array<TableEntry>,
      columns: [] as Array<Column>,
      actions: [
        {
          name: "edit",
          icon: "mdi-pencil",
          callback: (entry: TableEntry) => {
            console.debug("Call 'Edit' for", entry);
            this.$router.push({name: this.context.type.name, params: {id: entry.entity.id}});
          }
        },
        {
          name: "delete",
          icon: "mdi-delete",
          callback: async (entry: TableEntry) => {
            console.debug("Call 'Delete' for", entry);
            const promise = await GenericService.delete(entry.entity);

            if (promise === true) {
              this.successMessage(this.$i18n.t("messages.deleted", {entity: this.context.type.label.labels[this.currentLocale]}));
              this.fetchAll();
            }
          }
        }
      ] as Array<Action>,
      paging: {
        page: 1,
        pageSize: 15,
        totalPages: 1,
        totalEntries: 0
      },
      sorting: [] as Array<SortItem>,
      filters: [] as Array<FilterBlock>
    }
  },
  methods: {
    fetchAll: function () {
      const currentType = this.context.type;

      if (this.isLoading) {
        return;
      }
      this.isLoading = true;

      let propertySelection: Array<String> = this.getColumns().map(column => column.member.name);

      GenericService.fetchAll(
          currentType.typeId,
          propertySelection,
          this.paging.page,
          this.paging.pageSize,
          this.sorting,
          this.assembleFilter())
          .then((data) => {
            this.entries = [];
            data.content.forEach(entity => {
              this.entries.push({
                entity: entity
              });
            });
            this.paging.page = data.number + 1;
            this.paging.pageSize = data.size;
            this.paging.totalPages = data.totalPages;
            this.paging.totalEntries = data.totalElements;
          })
          .catch(error => console.error(error))
          .finally(() => this.buildListView());
    },
    getColumns: function (): Array<PDColumn> {
      return this.context.columns;
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
      return {
        blocks: [...this.filters],
      };
    },
    navigateToCreate: function (): void {
      this.$router.push({name: `create-${this.context.type.name}`});
    }
  },
  watch: {
    context: function () {
      this.contextChanged = true;
      this.entries = [];
      this.columns = [];
      this.sorting = [] as Array<SortItem>;
      this.filters = [] as Array<FilterBlock>;
      // this.actions = [];
      this.fetchAll();
    }
  },
  mounted() {
    this.fetchAll();
  },
});
</script>