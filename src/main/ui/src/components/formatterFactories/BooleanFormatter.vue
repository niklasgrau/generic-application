<template>
  <column-component :context="context" :unwrap-column="uiFactory.unwrapColumn">
    <template v-if="mode === 'text'">
      {{ formattedModel(model) }}
    </template>
    <template v-if="mode !== 'text' && mode !== 'icon'">
      {{ model }}
    </template>
    <v-icon v-if="mode === 'icon' && Boolean(model)" :icon="trueIcon" color="green"></v-icon>
    <v-icon v-if="mode === 'icon' && !Boolean(model)" :icon="falseIcon" color="red"></v-icon>
  </column-component>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import AbstractFormatterFactory from "@/components/formatterFactories/AbstractFormatterFactory.vue";

/**
 * Formatter for boolean values.
 * @author Kevin Link
 *
 * @param mode To show value represented by "icon" or "text".
 * @param trueIcon Name of the icon, if the value is true.
 * @param falseIcon Name of the icon, if the value is false.
 * @param trueValue Value shown, if true.
 * @param falseValue Value shown, if false.
 */
export default defineComponent({
  name: "BooleanFormatter",
  extends: AbstractFormatterFactory,
  data() {
    return {
      mode: this.context.formatterFactoryParams?.["mode"] ?? "icon",
      trueIcon: this.context.formatterFactoryParams?.["trueIcon"] ?? "mdi-check-circle-outline",
      falseIcon: this.context.formatterFactoryParams?.["falseIcon"] ?? "mdi-close-circle-outline",
      trueValue: this.context.formatterFactoryParams?.["trueValue"] ?? "culture.true",
      falseValue: this.context.formatterFactoryParams?.["falseValue"] ?? "culture.false",
    }
  },
  methods: {
    formattedModel: function (model: object): string {
      return Boolean(model) ? this.$i18n.t(this.trueValue) : this.$i18n.t(this.falseValue);
    }
  }
});
</script>
