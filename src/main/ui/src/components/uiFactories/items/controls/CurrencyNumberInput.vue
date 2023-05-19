<template>
  <column-component :context="context" :unwrap-column="uiFactory.unwrapColumn">
    <v-text-field
        v-model="model"
        :label="label(context.label?.labels, context.member?.required)"
        :disabled="context.disabled"
        :type="type"
        :rules="[rules.required, rules.validators]"
        clearable
        :prefix="uiFactory.currency"
        :density="uiFactory.density"
        :hide-details="uiFactory.hideDetails"
        :placeholder="uiFactory.placeholder"
    />
  </column-component>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import NumberInput from "@/components/uiFactories/items/controls/NumberInput.vue";

/**
 * Wrapper component for a currency number input.
 * @author Niklas Grau
 *
 * @param currency String to define the shown currency.
 */
export default defineComponent({
  name: "CurrencyNumberInput",
  extends: NumberInput,
  data() {
    return {
      uiFactory: {
        ...this.context.uiFactoryParams,
        currency: this.context.uiFactoryParams?.['currency'] ?? '€'
      }
    }
  }
});
</script>
