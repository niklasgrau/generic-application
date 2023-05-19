<script lang="ts">
import {defineComponent, type PropType} from "vue";
import AbstractUiFactory from "@/components/uiFactories/AbstractUiFactory.vue";
import ColumnComponent from "@/components/uiFactories/ColumnComponent.vue";
import {GenericEntity} from "@/models/runtime/GenericEntity";
import type {PDDetailViewGroupItemMeta} from "@/models/view/PDDetailViewGroupItemMeta";

/**
 * Abstract component for inputs. Providing entity property holding the complete property from api source.
 * @author Kevin Link
 */
export default defineComponent({
  name: "AbstractItemComponent",
  extends: AbstractUiFactory,
  components: {ColumnComponent},
  props: {
    context: {
      type: Object as PropType<PDDetailViewGroupItemMeta>,
      required: true
    },
    entity: {
      type: GenericEntity,
      required: false
    }
  },
  data() {
    return {
      uiFactory: {
        placeholder: this.context.uiFactoryParams?.['placeholder'] ?? null,
        ignoreValidation: this.context.uiFactoryParams?.['ignoreValidation'] ?? false,
        hideDetails: this.context.uiFactoryParams?.['hideDetails'] ?? false,
        unwrapColumn: this.context.uiFactoryParams?.['unwrapColumn'] ?? this.unwrapColumn,
        density: this.context.uiFactoryParams?.['density'] ?? "default"
      },
      rules: {
        required: (value: any) => {
          if (this.uiFactory.ignoreValidation) return true;
          if (this.context.member?.required) {
            return !!value || this.$t("components.validation.required");
          }
          return true;
        },
        validators: (value: any) => {
          if (this.uiFactory.ignoreValidation) return true;
          for (const validator of this.context.member?.validators) {
            const regex = new RegExp(validator.expression, "g")

            // If this validator fails, return the translated error message
            if (!regex.test(value)) {
              return validator.errorMessage.labels[this.currentLocale];
            }
          }
          // If none of the validators had a failure everything is fine
          return true;
        }
      }
    }
  },
  methods: {
    label: function (labels: Map<string, string>, required?: boolean): string {
      if (!labels || !this.context.labelVisible) return "";
      return labels[this.currentLocale] + (required ? '*' : '');
    }
  }
});
</script>
