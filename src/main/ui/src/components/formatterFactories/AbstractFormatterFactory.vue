<script lang="ts">
import {defineComponent} from "vue";
import AbstractItemComponent from "@/components/uiFactories/items/AbstractItemComponent.vue";

/**
 * Abstract component to group all formatter factories and provide params from metadata.
 * @author Kevin Link
 */
export default defineComponent({
  name: "AbstractFormatterFactory",
  extends: AbstractItemComponent,
  data() {
    return {
      formatterFactory: this.context.formatterFactoryParams,
    }
  },
  methods: {
    formattedModel: function (model: object): string {
      return model;
    }
  }
});
</script>
