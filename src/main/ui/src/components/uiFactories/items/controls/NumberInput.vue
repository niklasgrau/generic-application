<template>
  <column-component :context="context" :unwrap-column="uiFactory.unwrapColumn">
    <v-text-field
        v-model="model"
        :label="label(context.label?.labels, context.member?.required)"
        :disabled="context.disabled"
        :type="type"
        :rules="[rules.required, rules.validators]"
        clearable
        :density="uiFactory.density"
        :hide-details="uiFactory.hideDetails"
        :placeholder="uiFactory.placeholder"
    />
  </column-component>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import AbstractItemComponent from "@/components/uiFactories/items/AbstractItemComponent.vue";

/**
 * Wrapper component for a number input.
 * @author Niklas Grau
 */
export default defineComponent({
  name: "NumberInput",
  extends: AbstractItemComponent,
  data() {
    return {
      type: "number",
    }
  }
});
</script>
