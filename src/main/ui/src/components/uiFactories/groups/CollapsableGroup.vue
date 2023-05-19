<template>
  <column-component :context="context">
    <v-expansion-panels>
      <v-expansion-panel>
        <v-expansion-panel-title>
          {{ context.label?.labels[currentLocale] }}
        </v-expansion-panel-title>
        <v-expansion-panel-text>
          <!-- Children (Rows, TabGroups, Cards, Collapsables, etc.) -->
          <component v-for="child in context.groups"
                     :is="child.uiFactory"
                     :context="child"
                     :detailViewContext="detailViewContext"
                     v-model="model"
          />

          <!-- Items (PDMemberMeta or PDDetailViewGroupToManyAssociationItemMeta) -->
          <component v-for="item in context.items"
                     :is="item.uiFactory"
                     :context="item"
                     :entity="model"
                     :detailViewContext="detailViewContext"
                     v-model="model.properties[item.name]"
          />
        </v-expansion-panel-text>
      </v-expansion-panel>
    </v-expansion-panels>
  </column-component>
</template>

<script lang="ts">
import {defineComponent, type PropType} from "vue";
import AbstractGroupComponent from "@/components/uiFactories/groups/AbstractGroupComponent.vue";
import type {PDDetailViewGroupCollapsableMeta} from "@/models/view/PDDetailViewGroupCollapsableMeta";

/**
 * Wrapper component to provide a collapsable content area with title.
 * @author Niklas Grau
 */
export default defineComponent({
  name: "CollapsableGroup",
  extends: AbstractGroupComponent,
  props: {
    context: {
      type: Object as PropType<PDDetailViewGroupCollapsableMeta>,
      required: true,
    },
  },
});
</script>
