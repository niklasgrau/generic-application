<template>
  <div v-for="[firstLetter, registryTypes] in generateTypeRegistry">
    <v-card>
      <v-card-title>{{ firstLetter }}</v-card-title>
      <v-card-text>
        <v-list>
          <v-list-item
              v-for="type in registryTypes"
              :key="type.name"
              :title="type.labelPlural.labels[currentLocale]"
              :to="type.subsystem?.length > 0 ? `/${type.subsystem}/${type.name}s` : `/${type.name}s`"
              :disabled="!$router.hasRoute(`${type.name}s`)"
          />
        </v-list>
      </v-card-text>
    </v-card>
    <br/>
  </div>
</template>

<script lang="ts">
import {defineComponent, type PropType} from "vue";
import type {PDTypeMeta} from "@/models/core/PDTypeMeta";

/**
 * View of the type registry.
 * @author Niklas Grau
 */
export default defineComponent({
  name: "TypeRegistryView",
  inject: ["currentLocale"],
  props: {
    types: {
      type: Array as PropType<Array<PDTypeMeta>>,
      required: true
    },
  },
  computed: {
    generateTypeRegistry: function (): Map<string, PDTypeMeta[]> {
      let typeRegistry = new Map<string, PDTypeMeta[]>();

      for (let type of this.types) {
        // Get the first letter of the types label
        let label = type.label.labels[this.currentLocale] as string;
        const firstLetter = label.charAt(0);

        // If the letter is not already in the map, add it with an empty list as the value
        if (!typeRegistry.has(firstLetter)) {
          typeRegistry.set(firstLetter, []);
        }
        // Add the type to the list for that letter
        typeRegistry.get(firstLetter).push(type);
      }

      // Sort the Map
      const sortedArray = Array.from(typeRegistry).sort((a, b) => a[0].localeCompare(b[0]));
      typeRegistry = new Map(sortedArray);

      // Return the map
      return typeRegistry;
    }
  },
});
</script>
