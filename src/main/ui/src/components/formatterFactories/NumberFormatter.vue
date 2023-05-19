<template>
  <column-component :context="context" :unwrap-column="uiFactory.unwrapColumn">
    {{ formattedModel(model) }}
  </column-component>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import AbstractFormatterFactory from "@/components/formatterFactories/AbstractFormatterFactory.vue";

/**
 * Formatter for numeric values.
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Intl/NumberFormat/NumberFormat
 * @author Kevin Link
 *
 * Parameters supported can be found in the link above.
 *
 * @param locales Locales to format number.
 *
 */
export default defineComponent({
  name: "NumberFormatter",
  extends: AbstractFormatterFactory,
  methods: {
    formattedModel: function (model: object): string {
      return new Intl.NumberFormat(this.formatterFactory.locales, this.formatterFactory).format(Number(model));
    }
  }
});
</script>
