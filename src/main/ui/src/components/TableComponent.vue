<template>
  <v-table>
    <thead>
    <tr>
      <th v-for="column in columns" class="text-left" :style="{ width : `${column.width}%` }">
        <div class="d-flex align-center justify-space-between">
          {{ column.label?.labels[currentLocale] }}
          <v-tooltip v-if="sortable" :text="sortTooltip(column)" location="top">
            <template v-slot:activator="{ props }">
              <v-btn v-if="column.sortable" variant="plain"
                     :icon="sortIcon(column)" size="small"
                     @click="sortBy(column)" v-bind="props"/>
            </template>
          </v-tooltip>
        </div>
      </th>
      <th v-if="hasActions" class="w-0">{{ $t("listView.actions") }}</th>
    </tr>
    </thead>
    <tbody>
    <tr v-if="entries && entries.length === 0">
      <td :colspan="columns.length">{{ $t('listView.emptyState') }}</td>
    </tr>
    <tr v-for="entry in entries">
      <td v-for="(column, index) in columns" :class="entry?.color">
        <template v-if="!column.formatterFactory">
          {{ getValue(entry, column) }}
        </template>
        <component v-else :is="column.formatterFactory"
                   :context="getPreparedColumnMeta(column.meta)"
                   v-model="entry.entity.properties[column.name]"/>
      </td>
      <td v-if="hasActions" class="text-center" :class="entry?.color">
        <div class="d-flex">
          <v-btn v-for="action in actions" v-if="!entry.actions || entry.actions.length === 0"
                 :icon="action.icon"
                 :color="action.color ?? null"
                 @click="callAction(action, entry)"
                 variant="text"
                 size="x-small"/>

          <v-btn v-for="action in entry?.actions" v-else
                 :icon="action.icon"
                 :color="action.color ?? null"
                 @click="callAction(action, entry)"
                 variant="text"
                 size="x-small"/>
        </div>
      </td>
    </tr>
    </tbody>
  </v-table>
</template>

<script lang="ts">
import {defineComponent, type PropType} from "vue";
import type {Action} from "@/models/Action";
import type {Column} from "@/models/Column";
import type {SortItem} from "@/models/SortItem";
import type {GenericEntity} from "@/models/runtime/GenericEntity";
import type {TableEntry} from "@/models/TableEntry";
import type {PDColumn} from "@/models/view/PDColumn";

/**
 * Component to build up tables from api result.
 * @author Kevin Link
 */
export default defineComponent({
  name: "TableComponent",
  inject: ['currentLocale'],
  emits: ['sort'],
  props: {
    columns: {
      type: Array as PropType<Array<Column>>,
      required: true
    },
    actions: {
      type: Array as PropType<Array<Action>>,
      required: true
    },
    entries: {
      type: Array as PropType<Array<TableEntry>>,
      required: true
    },
    sorting: {
      type: Array as PropType<Array<SortItem>>,
      required: false
    },
    sortable: {
      type: Boolean,
      required: false,
      default: true
    }
  },
  methods: {
    getSorting: function (column: Column): SortItem | undefined {
      return this.sorting?.find(sort => sort.propertyName === column.name);
    },
    sortBy: function (column: Column) {
      let sort = this.getSorting(column);

      if (sort) {
        if (sort.isAscending) {
          this.sorting?.splice(this.sorting?.indexOf(sort), 1);
        } else {
          sort.isAscending = !sort.isAscending;
        }
      } else {
        sort = {
          propertyName: column.name,
          isAscending: false
        }
        this.sorting?.push(sort);
      }
      this.$emit("sort", null);
    },
    sortIcon: function (column: Column): string {
      let sort = this.getSorting(column);

      return this.getValueBySortState(sort,
          'mdi-sort-ascending',
          'mdi-sort-descending',
          'mdi-sort-variant-remove');
    },
    sortTooltip: function (column: Column): string {
      let sort = this.getSorting(column);

      return this.getValueBySortState(sort,
          this.$i18n.t('listView.sort.ascending'),
          this.$i18n.t('listView.sort.descending'),
          this.$i18n.t('listView.sort.reset'));
    },
    getValueBySortState: function (sortState: SortItem | undefined, ascending: string, descending: string, none: string): string {
      if (sortState) {
        return sortState.isAscending ? none : ascending;
      }
      return descending;
    },
    callAction: function (action: Action, entry: TableEntry) {
      if (action.callback) {
        action.callback(entry);
      }
    },
    getPreparedColumnMeta: function (meta: PDColumn) {
      if (!meta) return;

      meta.uiFactoryParams['unwrapColumn'] = true;

      return meta;
    },
    getValue: function (entry: TableEntry, column: Column): string {
      return (entry.entity.properties && entry.entity.properties[column.name]) ? entry.entity.properties[column.name] : "-";
    }
  },
  computed: {
    hasActions: function () {
      return this.actions && this.actions.length > 0;
    }
  }
});
</script>
