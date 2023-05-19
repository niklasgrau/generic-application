<template>
  <column-component :context="context" :unwrap-column="uiFactory.unwrapColumn">
    <v-select
        v-model="model"
        :items="context.member?.values.values"
        :item-value="item => item.name"
        :item-title="item => item.label?.labels[currentLocale]"
        :label="label(context.label?.labels, context.member?.required)"
        :rules="[rules.required, rules.validators]"
        persistent-hint
        :density="uiFactory.density"
        :hide-details="uiFactory.hideDetails"
        :placeholder="uiFactory.placeholder"
        clearable
    ></v-select>
  </column-component>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import AbstractItemComponent from "@/components/uiFactories/items/AbstractItemComponent.vue";

/**
 * Wrapper component for a list of enum values.
 * @author Kevin Link
 */
export default defineComponent({
  name: "EnumInput",
  extends: AbstractItemComponent
});
</script>
