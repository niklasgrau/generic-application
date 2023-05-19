<template>
  <column-component :context="context" :unwrap-column="uiFactory.unwrapColumn">
    <template v-if="entity.properties[context.member.name]">
      <label>{{ label(context.label?.labels, false) }}</label>
      <iframe
          :height="uiFactory.height"
          :width="uiFactory.width"
          :src="entity.properties[context.member.name]">
        {{ $t('components.fileInput.noFile') }}
      </iframe>
      <v-btn
          :href="fileDownload">
        {{ $t('components.fileInput.download') }}
      </v-btn>
    </template>
  </column-component>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import AbstractItemComponent from "@/components/uiFactories/items/AbstractItemComponent.vue";

/**
 * Wrapper component for a file preview.
 * @author Niklas Grau
 *
 * @param height Numeric value to set the height of the preview.
 * @param width Numeric value to set the width of the preview.
 */
export default defineComponent({
  name: "FilePreview",
  extends: AbstractItemComponent,
  computed: {
    fileDownload: function (): string {
      return `${this.entity.properties[this.context.member.name]}&download=true`
    },
  },
  data() {
    return {
      uiFactory: {
        height: this.context.uiFactoryParams?.['height'] ?? '400',
        width: this.context.uiFactoryParams?.['width'] ?? '100%'
      }
    }
  }
});
</script>
