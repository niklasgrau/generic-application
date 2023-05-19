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
    ></v-autocomplete>
  </column-component>
</template>

<script lang="ts">
import {defineComponent, type PropType} from "vue";
import {useApplicationStore} from "@/stores/application";
import {isGenericEntityProperty} from "@/models/runtime/GenericEntityProperty";
import AbstractItemComponent from "@/components/uiFactories/items/AbstractItemComponent.vue";
import {GenericService} from "@/services/GenericService";
import {GenericEntity} from "@/models/runtime/GenericEntity";
import {FilterBlockOperatorET} from "@/models/runtime/filter/FilterBlockOperatorET";
import {FilterOperatorET} from "@/models/runtime/filter/FilterOperatorET";
import {DataTypeET} from "@/models/core/DataTypeET";
import type {PDDetailViewGroupItemMeta} from "@/models/view/PDDetailViewGroupItemMeta";
import type {FilterStatement} from "@/models/runtime/filter/FilterStatement";
import type {PDTypeMeta} from "@/models/core/PDTypeMeta";
import type {PDAttributeMeta} from "@/models/core/PDAttributeMeta";

/**
 * Wrapper component to search and select one associated entity.
 * @author Niklas Grau
 */
export default defineComponent({
  name: "AutocompleteAssociation",
  extends: AbstractItemComponent,
  props: {
    context: {
      type: Object as PropType<PDDetailViewGroupItemMeta>,
      required: true,
    }
  },
  setup() {
    const appStore = useApplicationStore();
    return {
      application: appStore.app
    }
  },
  data() {
    return {
      isLoading: false,
      itemsLoaded: false,
      componentModel: null as GenericEntity | null,
      items: [] as Array<GenericEntity>,
      search: null,
      contextType: this.context.type as PDTypeMeta
    }
  },
  methods: {
    stringify: function (entity: GenericEntity): string {
      return entity?.objectStringifier.toString();
    },
    searchAssociations: function (searchTerm: string) {
      this.isLoading = true;
      this.itemsLoaded = false;
      this.items = [];

      // Fetching associations for the component by searching via stringifier attributes
      let filterStatement: FilterStatement = {
        blocks: [],
      }

      this.doForEachStringifierAttribute((attribute: PDAttributeMeta) => {
        filterStatement.blocks.push(
            {
              operator: FilterBlockOperatorET.OR,
              statements: [
                {
                  memberName: attribute.name,
                  operator: FilterOperatorET.LIKE,
                  value: `%${searchTerm}%`,
                }
              ],
            }
        );
      });

      GenericService.fetchAll(this.contextType.typeId, [], 1, 15, [], filterStatement)
          .then(json => {
            this.items = json.content.map((entity: GenericEntity) => {
              return new GenericEntity(entity.typeId, entity.id, entity.properties, this.contextType);
            });
          })
          .finally(() => {
            this.isLoading = false;
            this.itemsLoaded = true;
          });
    },
    doForEachStringifierAttribute: function (func: Function) {
      this.contextType.objectStringifier.attributes.forEach((attributeName: string) => {
        const typeAttribute = this.contextType.attributes.find(tAttr => tAttr.name === attributeName);
        if (typeAttribute?.dataType === DataTypeET.STRING || typeAttribute?.dataType === DataTypeET.NUMBER) {
          func(typeAttribute);
        }
      });
    }
  },
  watch: {
    componentModel: function (newModel: GenericEntity) {
      this.model = newModel
    },
    search(searchTerm) {
      if (searchTerm && searchTerm !== this.componentModel?.objectStringifier) {
        this.searchAssociations(searchTerm);
      }
    },
  },
  computed: {
    searchableAttributes: function () {
      let stringOfAttributes: string[] = [];
      this.doForEachStringifierAttribute((attribute: PDAttributeMeta) => {
        stringOfAttributes.push(attribute.label.labels[this.currentLocale]);
      });
      return stringOfAttributes.join(', ');
    }
  },
  async created() {
    if (!this.model || !isGenericEntityProperty(this.model)) return;

    this.isLoading = true;

    await GenericService.fetch(this.model.typeId, this.model.id, [])
        .then(json => {
          this.componentModel = new GenericEntity(json.typeId, json.id, json.properties, this.contextType)
        }).finally(() => {
          this.isLoading = false;
          this.itemsLoaded = true;
        });
  }
});
</script>