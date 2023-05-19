<template>
  <v-app-bar color="primary" dense>
    <template v-slot:prepend>
      <v-app-bar-nav-icon @click.stop="$emit('toggleDrawer', drawer = !drawer)"></v-app-bar-nav-icon>
    </template>

    <v-toolbar-title>{{ context.label.labels[currentLocale] }}</v-toolbar-title>

    <v-spacer></v-spacer>

    <v-menu open-on-hover>
      <template v-slot:activator="{ props }">
        <v-btn class="text-capitalize" v-bind="props" text>
          <v-icon left>mdi-translate</v-icon>
          {{ activeLanguage }}
          <v-icon right>mdi-menu-down</v-icon>
        </v-btn>
      </template>

      <v-list dense>
        <v-list-item v-for="language in languages" :key="language.code" @click="changeLanguage(language)">
          <v-list-item-title>{{ language.label }}</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-menu>

    <v-menu open-on-hover>
      <template v-slot:activator="{ props }">
        <v-btn icon v-bind="props">
          <v-icon>mdi-dots-vertical</v-icon>
        </v-btn>
      </template>

      <v-list dense>
        <v-list-item to="/type-registry">
          <v-list-item-title>{{ $t("nav.typeRegistry") }}</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-menu>
  </v-app-bar>
</template>

<script lang="ts">
import type {PDApplication} from "@/models/core/PDApplication";
import type {Language} from "@/models/Language";
import {setLocale} from "@/i18n";
import type {PropType} from "vue";

/**
 * Component representing top navigation bar.
 * @author Kevin Link
 */
export default {
  name: "AppBar",
  emits: ["toggleDrawer"],
  inject: ["currentLocale"],
  props: {
    context: {
      type: Object as PropType<PDApplication>,
      required: true
    }
  },
  i18n: {
    messages: {
      de: {
        login: "Anmelden",
        register: "Registrieren"
      },
      en: {
        login: "Login",
        register: "Register"
      }
    }
  },
  data() {
    return {
      languageMenu: false,
      drawer: false
    }
  },
  methods: {
    changeLanguage: function (lang: Language) {
      setLocale(lang.code);
      document.title = this.context.label?.labels[this.currentLocale];
      console.log("Language changed to:", lang);
    }
  },
  computed: {
    activeLanguage: function (): string {
      return this.currentLocale;
    },
    languages: function (): Array<Language> {
      return [
        {
          code: "de",
          label: "Deutsch"
        },
        {
          code: "en",
          label: "English"
        }
      ]
    }
  }
}
</script>