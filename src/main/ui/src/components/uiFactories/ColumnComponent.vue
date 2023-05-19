<template>
  <v-col :cols="context.colLG" :offset="context.colOffset" :sm="context.colSM" :md="context.colMD" :lg="context.colLG" v-if="!unwrapColumn">
    <slot/>
  </v-col>
  <slot v-if="unwrapColumn"/>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import AbstractUiFactory from "@/components/uiFactories/AbstractUiFactory.vue";

/**
 * Component to wrap other components in a slot to fit into the vuetify grid system.
 * @author Kevin Link
 */
export default defineComponent({
  name: "ColumnComponent",
  extends: AbstractUiFactory
});
</script>