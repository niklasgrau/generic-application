<template>
  <column-component :context="context" :unwrap-column="uiFactory.unwrapColumn">
    {{ formattedModel(model) }}
  </column-component>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import AbstractFormatterFactory from "@/components/formatterFactories/AbstractFormatterFactory.vue";
import type {PDEnumMeta} from "@/models/core/PDEnumMeta";
import type {PDAttributeMeta} from "@/models/core/PDAttributeMeta";

/**
 * Formatter for enum values.
 * @author Kevin Link
 */
export default defineComponent({
  name: "EnumFormatter",
  extends: AbstractFormatterFactory,
  methods: {
    formattedModel: function (model: object): string {
      let value = (<PDAttributeMeta>this.context.member).values?.values.find(value => value.name === String(model));
      return value ? String(value.label?.labels[this.currentLocale]) : '';
    }
  }
});
</script>
