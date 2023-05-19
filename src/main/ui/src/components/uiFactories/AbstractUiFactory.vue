<script lang="ts">
import {defineComponent} from "vue";
import {DetailViewContext} from "@/models/runtime/DetailViewContext";
import AbstractComponent from "@/components/AbstractComponent.vue";

/**
 * Abstract component for ui factories. Providing a dedicated detailViewContext for further information.
 * @author Kevin Link
 */
export default defineComponent({
  name: "AbstractUiFactory",
  extends: AbstractComponent,
  props: {
    detailViewContext: {
      type: DetailViewContext,
      required: false
    },
    unwrapColumn: {
      type: Boolean,
      required: false,
      default: false
    }
  },
  data() {
    return {
      uiFactory: {
        unwrapColumn: this.context.uiFactoryParams?.['unwrapColumn'] ?? this.unwrapColumn,
      }
    }
  }
});
</script>
