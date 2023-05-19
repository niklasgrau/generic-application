<template>
  <v-layout v-if="application.applicationMeta">
    <app-bar :context="application.applicationMeta" @toggleDrawer="drawerState = !drawerState"/>

    <navigation-menu :context="application.applicationMeta" :visible="drawerState"/>

    <v-main>
      <breadcrumb-menu :context="application.applicationMeta"/>
      <v-container>
        <notification-component :messages="notifications"/>
        <router-view :key="this.route.name"/>
      </v-container>
    </v-main>
  </v-layout>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import {useApplicationStore} from "@/stores/application";
import AppBar from "@/components/AppBarComponent.vue";
import NavigationMenu from "@/components/NavigationMenuComponent.vue";
import BreadcrumbMenu from "@/components/BreadcrumbMenuComponent.vue";
import NotificationComponent from "@/components/NotificationComponent.vue";
import {useRoute} from "vue-router";

/**
 * Root component to be the starting point of the application contents.
 * @author Kevin Link
 */
export default defineComponent({
  components: {NotificationComponent, BreadcrumbMenu, NavigationMenu, AppBar},
  inject: ["currentLocale"],
  setup() {
    const applicationStore = useApplicationStore();
    const route = useRoute();

    return {
      applicationStore,
      application: applicationStore.app,
      route
    }
  },
  data() {
    return {
      notifications: [],
      drawerState: true
    }
  },
  watch: {
    "applicationStore.notifications": {
      handler(newValue) {
        // Watch for new notifications to show
        this.notifications = newValue;
      },
      deep: true
    }
  },
  created() {
    document.title = this.application.applicationMeta.label?.labels[this.currentLocale];
  }
});
</script>