<template>
  <v-row :no-gutters="context.uiFactoryParams['no-gutters'] === 'true' ?? false">
    <!-- Children (Rows, TabGroups, Collapsables, Cards, etc.) -->
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
  </v-row>
</template>

<script lang="ts">
import {defineComponent, type PropType} from "vue";
import type {PDDetailViewGroupRowMeta} from "@/models/view/PDDetailViewGroupRowMeta";
import AbstractGroupComponent from "@/components/uiFactories/groups/AbstractGroupComponent.vue";

/**
 * Wrapper component to provide a simple grid system row.
 * @author Niklas Grau
 */
export default defineComponent({
  name: "RowGroup",
  extends: AbstractGroupComponent,
  props: {
    context: {
      type: Object as PropType<PDDetailViewGroupRowMeta>,
      required: true,
    },
  },
});
</script>
