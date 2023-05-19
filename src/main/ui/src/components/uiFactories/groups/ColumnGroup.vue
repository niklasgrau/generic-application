<template>
  <column-component :context="context">
    <component v-for="child in context.groups"
               :is="child.uiFactory"
               :context="child"
               :detailViewContext="detailViewContext"
               v-model="model"
    />
  </column-component>
</template>

<script lang="ts">
import {defineComponent, type PropType} from "vue";
import AbstractGroupComponent from "@/components/uiFactories/groups/AbstractGroupComponent.vue";
import type {PDDetailViewGroupColumnMeta} from "@/models/view/PDDetailViewGroupColumnMeta";

/**
 * Wrapper component to represent a grid column and group items in a row.
 * @author Kevin Link
 */
export default defineComponent({
  name: "ColumnGroup",
  extends: AbstractGroupComponent,
  props: {
    context: {
      type: Object as PropType<PDDetailViewGroupColumnMeta>,
      required: true,
    },
  },
});
</script>