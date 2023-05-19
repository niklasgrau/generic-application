<template>
  <column-component :context="context" :unwrap-column="uiFactory.unwrapColumn">
    <v-text-field
        v-model="model"
        :label="label(context.label?.labels, context.member?.required)"
        :disabled="context.disabled"
        type="date"
        :rules="[rules.required]"
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
import moment from "moment";

/**
 * Wrapper component for a date input.
 * @author Kevin Link
 */
export default defineComponent({
  name: "DateInput",
  extends: AbstractItemComponent,
  emits: ["update:model-value"],
  computed: {
    model: {
      // Proxy model to take and update parent model value
      get() {
        if (this.modelValue) {
          return moment(this.modelValue).format('YYYY-MM-DD')
        }
        return null;
      },
      set(newValue: string | number) {
        this.$emit('update:model-value', (newValue) ? moment(newValue + 'T00:00')
            .format('YYYY-MM-DDT00:00') : null);
      }
    }
  },
});
</script>
