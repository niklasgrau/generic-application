<template>
  <column-component :context="context" :unwrap-column="uiFactory.unwrapColumn">
    <v-text-field
        v-model="model"
        :label="label(context.label?.labels, context.member?.required)"
        :disabled="context.disabled"
        :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
        :type="showPassword ? 'text' : 'password'"
        :rules="[rules.required, rules.validators]"
        @click:append="showPassword = !showPassword"
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
 * Wrapper component for a password input.
 * @author Niklas Grau
 */
export default defineComponent({
  name: "PasswordInput",
  extends: AbstractItemComponent,
  data() {
    return {
      showPassword: false,
    }
  },
});
</script>
