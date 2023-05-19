<template>
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
</template>

<script lang="ts">
import {defineComponent, type PropType} from "vue";
import AbstractGroupComponent from "@/components/uiFactories/groups/AbstractGroupComponent.vue";
import type {PDDetailViewGroupTabMeta} from "@/models/view/PDDetailViewGroupTabMeta";

/**
 * Wrapper component to represent a tab in a TabContainerGroup component.
 * @author Niklas Grau
 */
export default defineComponent({
  name: "TabGroup",
  extends: AbstractGroupComponent,
  props: {
    context: {
      type: Object as PropType<PDDetailViewGroupTabMeta>,
      required: true,
    },
  },
});
</script>
