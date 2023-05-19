<template>
  <column-component :context="context" :unwrap-column="uiFactory.unwrapColumn">
    <v-switch
        v-model="model"
        :label="label(context.label?.labels, context.member.required)"
        color="primary"
        :disabled="context.disabled"
        :rules="[rules.required, rules.validators]"
        :density="uiFactory.density"
        :hide-details="uiFactory.hideDetails"
    ></v-switch>
  </column-component>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import AbstractItemComponent from "@/components/uiFactories/items/AbstractItemComponent.vue";

/**
 * Wrapper component for a toggle input.
 * @author Niklas Grau
 */
export default defineComponent({
  name: "ToggleInput",
  extends: AbstractItemComponent,
});
</script>
