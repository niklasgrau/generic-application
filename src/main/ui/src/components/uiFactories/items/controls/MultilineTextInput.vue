<template>
  <column-component :context="context" :unwrap-column="uiFactory.unwrapColumn">
    <v-textarea
        v-model="model"
        :label="label(context.label?.labels, context.member?.required)"
        :disabled="context.disabled"
        :rules="[rules.required, rules.validators]"
        clearable
        :counter="uiFactory.showCounter === 'true'"
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
 * Wrapper component for a multiline text input.
 * @author Niklas Grau
 *
 * @param showCounter Boolean value to toggle the character counter.
 */
export default defineComponent({
  name: "MultilineTextInput",
  extends: AbstractItemComponent,
  data() {
    return {
      uiFactory: {
        ...this.context.uiFactoryParams,
        showCounter: this.context.uiFactoryParams?.['showCounter'] ?? 'true'
      }
    }
  }
});
</script>
