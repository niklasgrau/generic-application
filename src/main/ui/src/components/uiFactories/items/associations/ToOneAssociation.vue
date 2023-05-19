<template>
  <column-component :context="context">
    <v-autocomplete
        v-model="componentModel"
        v-model:search="search"
        :loading="isLoading"
        :items="items"
        :item-value="item => item.id"
        :item-title="item => stringify(item)"
        :label="label(context.label?.labels, context.member.required)"
        :rules="[rules.required, rules.validators]"
        :no-data-text="$t('components.association.noData')"
        :hint="$t('components.association.hint', {fields: searchableAttributes})"
        persistent-hint
        return-object
        clearable
        no-filter
    ></v-autocomplete>
  </column-component>
</template>

<script lang="ts">
import {defineComponent, type PropType} from "vue";
import type {PDDetailViewGroupItemMeta} from "@/models/view/PDDetailViewGroupItemMeta";
import AutocompleteAssociation from "@/components/uiFactories/items/associations/AutocompleteAssociation.vue";
import type {GenericEntityProperty} from "@/models/runtime/GenericEntityProperty";

/**
 * Wrapper component to search and select one associated entity.
 * @author Niklas Grau
 */
export default defineComponent({
  name: "ToOneAssociation",
  extends: AutocompleteAssociation,
  props: {
    context: {
      type: Object as PropType<PDDetailViewGroupItemMeta>,
      required: true,
    }
  },
  watch: {
    /**
     * Proxy to set the components model to a specific data structure to set in the GenericEntity property bag.
     * @param newModel New value of componentModel
     */
    componentModel: function (newModel: GenericEntityProperty) {
      this.model = {
        id: newModel?.id,
        typeId: newModel?.typeId,
      }
    },
  },
});
</script>