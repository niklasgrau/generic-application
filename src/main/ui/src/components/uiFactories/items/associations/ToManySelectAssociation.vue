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
        return-object
        :no-data-text="$t('components.association.noData')"
        :hint="$t('components.association.hint', {fields: searchableAttributes})"
        persistent-hint
        clearable
        no-filter
        chips
        closable-chips
        multiple
    ></v-autocomplete>
  </column-component>
</template>

<script lang="ts">
import {defineComponent, type PropType} from "vue";
import type {PDDetailViewGroupItemMeta} from "@/models/view/PDDetailViewGroupItemMeta";
import AutocompleteAssociation from "@/components/uiFactories/items/associations/AutocompleteAssociation.vue";

/**
 * Wrapper component to search and select many associated entities.
 * @author Niklas Grau
 */
export default defineComponent({
  name: "ToManySelectAssociation",
  extends: AutocompleteAssociation,
  props: {
    context: {
      type: Object as PropType<PDDetailViewGroupItemMeta>,
      required: true,
    }
  },
});
</script>