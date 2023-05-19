<script lang="ts">
import {defineComponent} from "vue";

/**
 * Abstract component to primarily provide context prop.
 * @author Kevin Link
 */
export default defineComponent({
  name: "AbstractComponent",
  inject: ["currentLocale"],
  emits: ["update:model-value"],
  props: {
    context: {
      type: Object,
      required: true
    },
    modelValue: {
      type: null,
      required: false
    },
  },
  computed: {
    model: {
      // Proxy model to take and update parent model value
      get() {
        return this.modelValue
      },
      set(newValue: string | number) {
        this.$emit('update:model-value', newValue);
      }
    }
  },
});
</script>
