<script setup lang="ts"></script>

<template>
  <v-row>
    <v-col v-for="data in dataset" cols="3">
      <v-card class="fill-height">
        <v-card-text>
          <v-row>
            <v-col cols="4">
              <v-icon size="xxx-large">{{ data[1].icon }}</v-icon>
            </v-col>
            <v-col cols="8" class="text-right">
              <v-row>
                <v-col>
                  <h3>{{ data[1].label.labels[currentLocale] }}</h3>
                </v-col>
              </v-row>
              <v-row>
                <v-col class="text-h2">
                  {{ data[1].entries }}
                </v-col>
              </v-row>
            </v-col>
          </v-row>
        </v-card-text>
      </v-card>
    </v-col>
  </v-row>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import {useApplicationStore} from "@/stores/application";
import type {PDTypeMeta} from "@/models/core/PDTypeMeta";
import {GenericService} from "@/services/GenericService";
import type {MultiLanguageLabel} from "@/models/core/MultiLanguageLabel";

interface DataSet {
  icon: string | undefined,
  label: MultiLanguageLabel,
  entries: number,
}

/**
 * View of the start page.
 * @author Niklas Grau
 */
export default defineComponent({
  name: "HomeView",
  inject: ["currentLocale"],
  props: {},
  setup() {
    const appStore = useApplicationStore();
    return {
      application: appStore.app,
      appStore,
    }
  },
  data() {
    return {
      dataset: new Map<string, DataSet>(),
    }
  },
  created() {
    this.fetchTypeDataset();
  },
  methods: {
    fetchTypeDataset: function () {
      this.application?.applicationMeta.types.forEach(async (type: PDTypeMeta) => {
        await GenericService.fetchAll(
            type.typeId,
            [],
            1,
            1,
            [],
            {blocks: []}
        ).then((data) => {
          this.dataset.set(type.name, {
            icon: type.icon,
            label: type.labelPlural,
            entries: data.totalElements,
          });
        }).catch(error => console.error(error));
      });
    },
  },
});
</script>
