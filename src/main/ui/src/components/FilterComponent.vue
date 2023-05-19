<template>
  <v-expansion-panels v-if="searchableColumns.length > 0">
    <v-expansion-panel>
      <v-expansion-panel-title>
        <template v-slot:default="{ expanded }">
          <v-row no-gutters>
            <v-col class="d-flex justify-start">
              <v-icon icon="mdi-filter" size="medium" class="pr-4"/>
              {{ $t('listView.filter.label') }}
            </v-col>
          </v-row>
        </template>
      </v-expansion-panel-title>
      <v-expansion-panel-text>
        <v-alert v-text="$t('listView.filter.emptyState')" type="info" density="compact"
                 v-if="model.length === 0" class="mb-4"/>
        <v-row v-for="(block, bIndex) in model" :key="bIndex">
          <v-col cols="1">
            <p class="font-weight-bold" v-if="bIndex === 0" v-text="$t('listView.filter.where')"/>
            <v-select v-model="block.operator" :items="blockOperators"
                      :item-value="item => item"
                      :item-title="item => $t(`listView.filter.operators.${item.toLowerCase()}`)"
                      density="compact" v-if="bIndex !== 0"/>
          </v-col>
          <v-col cols="10">
            <v-card>
              <v-card-text>
                <v-alert v-text="$t('listView.filter.emptyState')" type="info" density="compact"
                         v-if="block.statements.length === 0"/>
                <v-row v-for="(statement, sIndex) in block.statements" :key="`${sIndex}`">
                  <v-col cols="5">
                    <v-select v-model="statement.memberName" :items="searchableColumns"
                              :item-value="item => item.name"
                              :item-title="item => item.label?.labels[currentLocale]"
                              density="compact" :hide-details="true"/>
                  </v-col>
                  <v-col cols="2">
                    <v-select v-model="statement.operator" :items="filterOperators(statement)"
                              :item-value="item => item"
                              :item-title="item => $t(`listView.filter.operators.${item.toLowerCase()}`)"
                              density="compact" :hide-details="true"/>
                  </v-col>
                  <v-col cols="5">
                    <div class="d-flex align-center h-100">
                      <component v-if="statement.memberName && !booleanOperations.includes(statement.operator)"
                                 :is="getColumnByName(statement.memberName)?.uiFactory"
                                 :context="getItemMeta(statement.memberName)"
                                 v-model="statement.value"
                      />
                      <v-spacer v-if="booleanOperations.includes(statement.operator)"/>
                      <v-btn icon="mdi-filter-minus" density="compact" variant="plain" color="error" class="ml-3"
                             @click="removeFilterMember(block, sIndex)"/>
                    </div>
                  </v-col>
                </v-row>
              </v-card-text>
              <v-card-actions>
                <v-row>
                  <v-col>
                    <v-btn color="primary" prepend-icon="mdi-filter-plus" @click="addFilterMember(block)"
                           density="compact">
                      {{ $t('listView.filter.actions.add.filter') }}
                    </v-btn>
                  </v-col>
                </v-row>
              </v-card-actions>
            </v-card>
          </v-col>
          <v-col cols="1">
            <v-btn icon="mdi-filter-minus" density="compact" variant="plain" color="error"
                   @click="removeFilterBlock(bIndex)"/>
          </v-col>
        </v-row>

        <v-row justify="space-between">
          <v-col class="d-flex" style="gap: 1rem">
            <v-btn color="primary" prepend-icon="mdi-filter-plus" variant="text" @click="addFilterBlock">
              {{ $t('listView.filter.actions.add.filterBlock') }}
            </v-btn>
            <v-btn color="warning" prepend-icon="mdi-filter-remove" variant="text" @click="resetFilter">
              {{ $t('listView.filter.actions.reset') }}
            </v-btn>
          </v-col>
          <v-col class="text-right">
            <v-btn color="primary" prepend-icon="mdi-filter" @click="$emit('filter')">
              {{ $t('listView.filter.actions.search') }}
            </v-btn>
          </v-col>
        </v-row>
      </v-expansion-panel-text>
    </v-expansion-panel>
  </v-expansion-panels>
</template>

<script lang="ts">
import {defineComponent, type PropType} from "vue";
import type {PDAttributeMeta} from "@/models/core/PDAttributeMeta";
import {isPDAttributeMeta} from "@/models/core/PDAttributeMeta";
import {isPDAssociationMeta} from "@/models/core/PDAssociationMeta";
import TextInput from "@/components/uiFactories/items/controls/TextInput.vue";
import ToOneAssociation from "@/components/uiFactories/items/associations/ToOneAssociation.vue";
import {FilterBlockOperatorET} from "@/models/runtime/filter/FilterBlockOperatorET";
import {DataTypeET} from "@/models/core/DataTypeET";
import {FilterOperatorGroupFactory} from "@/factories/FilterOperatorGroupFactory";
import type {FilterOperatorET} from "@/models/runtime/filter/FilterOperatorET";
import type {FilterBlock} from "@/models/runtime/filter/FilterBlock";
import type {Column} from "@/models/Column";
import type {FilterMember} from "@/models/runtime/filter/FilterMember";
import type {PDMemberMeta} from "@/models/core/PDMemberMeta";
import type {PDListViewMeta} from "@/models/view/PDListViewMeta";
import type {PDColumn} from "@/models/view/PDColumn";
import AbstractComponent from "@/components/AbstractComponent.vue";

/**
 * Component to provide filter operations by list view columns (referencing type members).
 * @author Kevin Link
 */
export default defineComponent({
  name: "FilterComponent",
  extends: AbstractComponent,
  emits: ['filter', 'update:modelValue'],
  setup() {
    const blockOperators = Object.keys(FilterBlockOperatorET);

    return {
      blockOperators,
      filterFactory: new FilterOperatorGroupFactory(),
      booleanOperations: ['IS_TRUE', 'IS_FALSE']
    }
  },
  props: {
    context: {
      type: Object as PropType<PDListViewMeta>,
      required: true
    },
    modelValue: {
      type: Array as PropType<Array<FilterBlock>>,
      required: true
    }
  },
  methods: {
    resetFilter: function (): void {
      this.model = <Array<FilterBlock>>[];
    },
    addFilterBlock: function (): void {
      let filterBlock: FilterBlock = {
        operator: FilterBlockOperatorET.OR,
        statements: []
      };
      this.model = <Array<FilterBlock>>[...this.model, filterBlock];
    },
    removeFilterBlock: function (index: number): void {
      this.model.splice(index, 1);
    },
    addFilterMember: function (block: FilterBlock): void {
      let filterMember: FilterMember = {
        memberName: null,
        operator: null,
        value: null
      }
      block.statements = [...block.statements, filterMember];
    },
    removeFilterMember: function (block: FilterBlock, index: number): void {
      block.statements.splice(index, 1);
    },
    filterOperators: function (statement: FilterMember): FilterOperatorET[] {
      if (!statement.memberName) return [];

      const column: Column | undefined = this.searchableColumns.find(column => column.name === statement.memberName);

      if (!column) return [];

      const operators: FilterOperatorET[] = this.filterFactory.createOperatorGroup(column.type);

      // Reset operator selection, if no longer available due to column definition
      if (!operators.includes(statement.operator)) {
        statement.operator = operators[0];
      }

      return operators;
    },
    getColumnByName: function (name: string): Column | undefined {
      return this.searchableColumns.find(column => column.name === name);
    },
    getItemMeta: function (name: string): PDColumn | undefined {
      const itemMeta = this.context.columns.find((column: PDColumn) => column.name === name);
      itemMeta.uiFactoryParams['density'] = "compact";
      itemMeta.uiFactoryParams['hideDetails'] = true;
      itemMeta.uiFactoryParams['ignoreValidation'] = true;
      itemMeta.uiFactoryParams['placeholder'] = this.$i18n.t('listView.filter.value.placeholder');
      itemMeta.uiFactoryParams['unwrapColumn'] = true;
      return itemMeta;
    },
    buildColumn: function (member: PDMemberMeta | PDAttributeMeta, column: PDColumn, dataType: DataTypeET, defaultUiFactory: string | undefined): Column {
      return <Column>{
        name: member.name,
        label: member.label,
        sortable: column.sortable,
        searchable: column.searchable,
        width: column.width,
        type: dataType,
        uiFactory: column.uiFactory ?? defaultUiFactory,
        uiFactoryParams: column.uiFactoryParams
      };
    }
  },
  computed: {
    searchableColumns: function (): Array<Column> {
      if (!this.context?.columns) return [];

      const columns: Array<Column> = [];
      for (const column of this.context.columns) {
        const member: PDMemberMeta = column.member;

        if (!member || !column.searchable) continue;

        if (isPDAttributeMeta(member)) {
          let attributeMember: PDAttributeMeta = member as PDAttributeMeta;
          columns.push(this.buildColumn(member, column, attributeMember.dataType, TextInput.name));
        } else if (isPDAssociationMeta(member)) {
          columns.push(this.buildColumn(member, column, DataTypeET.STRING, ToOneAssociation.name));
        }
      }
      return columns;
    }
  }
});
</script>
