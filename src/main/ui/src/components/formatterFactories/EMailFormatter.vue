<template>
  <column-component :context="context" :unwrap-column="uiFactory.unwrapColumn">
    <a :href="`mailto:${model}`" v-if="model">{{ model }}</a>
    <template v-else v-text="model"/>
  </column-component>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import AbstractFormatterFactory from "@/components/formatterFactories/AbstractFormatterFactory.vue";

/**
 * Formatter for email values.
 * @author Kevin Link
 */
export default defineComponent({
  name: "EMailFormatter",
  extends: AbstractFormatterFactory,
  methods: {
    formattedModel: function (model: object): string {
      return String(model);
    }
  }
});
</script>
