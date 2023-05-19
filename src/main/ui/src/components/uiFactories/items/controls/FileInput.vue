<template>
  <column-component :context="context" :unwrap-column="uiFactory.unwrapColumn">
    <v-file-input
        v-model="file"
        :label="label(context.label?.labels, context.member?.required)"
        show-size
        :clearable="false"
        @change="handleFileUpload($event)"
        :density="uiFactory.density"
        :hide-details="uiFactory.hideDetails"
        :placeholder="uiFactory.placeholder"
        :append-icon="file ? 'mdi-delete' : null"
        @click:append="deleteFile"
    ></v-file-input>
  </column-component>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import AbstractItemComponent from "@/components/uiFactories/items/AbstractItemComponent.vue";

/**
 * Wrapper component for a file input.
 * @author Kevin Link
 */
export default defineComponent({
  name: "FileInput",
  extends: AbstractItemComponent,
  data() {
    return {
      file: undefined
    }
  },
  methods: {
    handleFileUpload: function (event: Event) {
      const file = event.target?.files[0];

      if (!this.detailViewContext) {
        throw new Error(`No detail view context available for file input '${this.context.member.name}'.`);
      }

      this.detailViewContext.setFile(this.context.member.name, file);
    },
    deleteFile: function () {
      if (!this.detailViewContext) {
        throw new Error(`No detail view context available for file input '${this.context.member.name}'.`);
      }
      this.file = undefined;
      this.detailViewContext.setFile(this.context.member.name, null);
    }
  }
});
</script>
