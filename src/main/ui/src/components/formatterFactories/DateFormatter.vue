<template>
  <column-component :context="context" :unwrap-column="uiFactory.unwrapColumn">
    {{ formattedModel(model) }}
  </column-component>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import moment from "moment";
import AbstractFormatterFactory from "@/components/formatterFactories/AbstractFormatterFactory.vue";

/**
 * Formatter for date values.
 * https://momentjs.com/docs/#/displaying/format/
 * @author Kevin Link
 *
 * @param format Format of the shown data value.
 */
export default defineComponent({
  name: "DateFormatter",
  extends: AbstractFormatterFactory,
  methods: {
    formattedModel: function (model: object): string {
      let format = this.formatterFactory.format ?? this.$i18n.t("culture.dateTimeFormat");
      return (model) ? moment(model).format(format) : '-';
    }
  }
});
</script>
