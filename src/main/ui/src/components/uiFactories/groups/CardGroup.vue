<template>
  <column-component :context="context">
    <v-card>
      <v-card-title v-if="context.labelVisible && context.label?.labels">
        {{ context.label?.labels[currentLocale] }}
      </v-card-title>
      <v-card-text>
        <!-- Children (Rows, TabGroups, Collapsables, Cards, etc.) -->
        <component v-for="child in context.groups"
                   :is="child.uiFactory"
                   :context="child"
                   :detailViewContext="detailViewContext"
                   v-model="model"
        />

        <!-- Items (PDMemberMeta or PDDetailViewGroupToManyAssociationItemMeta) -->
        <component v-for="item in context.items"
                   :is="item.uiFactory"
                   :context="item"
                   :entity="model"
                   :detailViewContext="detailViewContext"
                   v-model="model.properties[item.name]"
        />
      </v-card-text>
    </v-card>
  </column-component>
</template>

<script lang="ts">
import {defineComponent, type PropType} from "vue";
import type {PDDetailViewGroupCardMeta} from "@/models/view/PDDetailViewGroupCardMeta";
import AbstractGroupComponent from "@/components/uiFactories/groups/AbstractGroupComponent.vue";

/**
 * Wrapper component represent a card-style container with title.
 * @author Niklas Grau
 */
export default defineComponent({
  name: "CardGroup",
  extends: AbstractGroupComponent,
  props: {
    context: {
      type: Object as PropType<PDDetailViewGroupCardMeta>,
      required: true,
    },
  }
});
</script>
