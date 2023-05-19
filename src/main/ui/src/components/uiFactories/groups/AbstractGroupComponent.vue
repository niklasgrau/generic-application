<script lang="ts">
import {defineComponent} from "vue";
import ColumnComponent from "@/components/uiFactories/ColumnComponent.vue";
import AbstractUiFactory from "@/components/uiFactories/AbstractUiFactory.vue";

/**
 * Abstract component to group all group components.
 * @author Kevin Link
 */
export default defineComponent({
  name: "AbstractGroupComponent",
  extends: AbstractUiFactory,
  components: {ColumnComponent},
});
</script>