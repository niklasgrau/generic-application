<template>
  <v-navigation-drawer v-model="visible" permanent>
    <v-list v-model:opened="open" density="compact" nav v-if="context && context.subsystems">
      <v-list-item prepend-icon="mdi-home" :title="$t('nav.home')" to="/"></v-list-item>

      <v-list-group v-for="subsystem in context.subsystems"
                    :value="subsystem.name">
        <!-- Show group item with activator -->
        <template v-slot:activator="{ props }">
          <v-list-item v-bind="props"
                       :prepend-icon="subsystem.icon"
                       :title="subsystem.label.labels[currentLocale]"/>
        </template>

        <!-- Show nav items of the subsystem -->
        <v-list-item v-for="navigationItem in getNavigationItemsBySubsystem(subsystem.name)"
                     :title="navigationItem.labelPlural.labels[currentLocale]"
                     :prepend-icon="navigationItem.icon"
                     :to="`/${navigationItem.subsystem}/${navigationItem.name}s`"
                     :active="isCurrentRoute(navigationItem.subsystem, navigationItem.name)"/>
      </v-list-group>

      <v-list-item v-for="navigationItem in getNavigationItemsWithoutSubsystem()"
                   :title="navigationItem.labelPlural.labels[currentLocale]"
                   :prepend-icon="navigationItem.icon"
                   :to="`/${navigationItem.name}s`"
                   :active="isCurrentRoute(null, navigationItem.name)"/>

      <v-list-item prepend-icon="mdi-table-of-contents" :title="$t('nav.typeRegistry')"
                   to="/type-registry"></v-list-item>

      <hr/>

      <!-- Dev tools -->
      <v-list-item prepend-icon="mdi-console" :title="$t('nav.databaseConsole')" href="http://localhost:8080/console"
                   target="_blank"></v-list-item>
    </v-list>
  </v-navigation-drawer>
</template>

<script lang="ts">
import {defineComponent, type PropType} from "vue";
import type {PDApplication} from "@/models/core/PDApplication";
import type {PDTypeMeta} from "@/models/core/PDTypeMeta";
import {useRoute} from "vue-router";

/**
 * Component to show navigation drawer menu on the left.
 * @author Niklas Grau
 */
export default defineComponent({
  name: "NavigationMenu",
  inject: ["currentLocale"],
  setup() {
    const route = useRoute();

    return {
      route
    }
  },
  props: {
    context: {
      type: Object as PropType<PDApplication>,
      required: true
    },
    visible: {
      type: Boolean,
      required: false
    }
  },
  data() {
    return {
      open: [] as Array<String>
    }
  },
  methods: {
    setupNavigation: function () {
      // Init "open" array for navigation group activator
      this.context.subsystems.forEach((subsystem) => {
        this.open.push(subsystem.name);
      })
    },
    getNavigationItemsBySubsystem: function (subsystemName: string) {
      let navItems: PDTypeMeta[] = [];
      this.context.navigation.forEach((navigationItem) => {
        if (navigationItem.subsystem === subsystemName) {
          navItems.push(navigationItem)
        }
      });

      return navItems;
    },
    getNavigationItemsWithoutSubsystem: function () {
      let navItems: PDTypeMeta[] = [];

      this.context.navigation.forEach((navigationItem) => {
        if (!navigationItem.subsystem) {
          navItems.push(navigationItem)
        }
      });

      return navItems;
    },
    isCurrentRoute: function (subsystem: string, name: string): boolean {
      return this.route.name?.includes(name) && (subsystem == null || subsystem === this.route.meta.subsystem?.name);
    }
  },
  mounted() {
    this.setupNavigation();
  }
});
</script>
