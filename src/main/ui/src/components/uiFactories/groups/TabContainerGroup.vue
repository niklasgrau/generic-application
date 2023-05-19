<template>
  <column-component :context="context">
    <v-card>
      <v-tabs v-model="tab" color="primary">
        <v-tab
            v-for="child in context.groups"
            :value="child.name"
        >
          {{ child.label?.labels[currentLocale] }}
        </v-tab>
      </v-tabs>

      <v-card-text>
        <v-window v-model="tab">
          <v-window-item
              v-for="child in context.groups"
              :value="child.name"
          >
            <!-- Children (only Tabs) -->
            <component :is="child.uiFactory"
                       :context="child"
                       :detailViewContext="detailViewContext"
                       v-model="model"
            />
          </v-window-item>
        </v-window>
      </v-card-text>
    </v-card>
  </column-component>
</template>

<script lang="ts">
import {defineComponent, type PropType} from "vue";
import type {PDDetailViewGroupRowMeta} from "@/models/view/PDDetailViewGroupRowMeta";
import TabGroup from "@/components/uiFactories/groups/TabGroup.vue";
import AbstractGroupComponent from "@/components/uiFactories/groups/AbstractGroupComponent.vue";

/**
 * Wrapper component to wrap a number of TabGroups.
 * @author Niklas Grau
 */
export default defineComponent({
  name: "TabContainerGroup",
  extends: AbstractGroupComponent,
  components: {TabGroup},
  inject: ["currentLocale"],
  props: {
    context: {
      type: Object as PropType<PDDetailViewGroupRowMeta>,
      required: true,
    },
  },
  data: () => ({
    tab: null,
  }),
});
</script>
