<template>
  <v-row>
    <v-col>
      <v-form ref="form">
        <!-- Groups -->
        <component v-if="entity"
                   v-for="group in detailViewContext.detailViewMeta.groups"
                   :is="group.uiFactory"
                   :context="group"
                   :detailViewContext="detailViewContext"
                   v-model="entity"
                   :key="refreshKey">
        </component>
      </v-form>
    </v-col>
  </v-row>

  <!-- CRUD Operations -->
  <v-row v-if="entity">
    <v-col>
      <v-btn v-if="!creationMode" @click="saveEntity" color="primary" class="mr-4" :loading="isSaving">
        {{ $t('actions.apply') }}
      </v-btn>
      <v-btn v-if="creationMode" @click="saveEntity" color="primary" class="mr-4" :loading="isSaving">
        {{ $t('actions.save') }}
      </v-btn>
      <v-btn @click="toListView" color="grey" class="mr-4">
        {{ $t('actions.cancel') }}
      </v-btn>
    </v-col>
    <v-col class="d-flex justify-end">
      <v-btn @click="deleteEntity" color="error" :loading="isDeleting">
        {{ $t('actions.delete') }}
      </v-btn>
    </v-col>
  </v-row>
</template>

<script lang="ts">
import {defineComponent, type PropType} from "vue";
import {GenericEntity} from "@/models/runtime/GenericEntity";
import {GenericService} from "@/services/GenericService";
import {useApplicationStore} from "@/stores/application";
import {DetailViewContext} from "@/models/runtime/DetailViewContext";
import {FileService} from "@/services/FileService";
import {useRoute} from "vue-router";
import type {PDTypeMeta} from "@/models/core/PDTypeMeta";
import type {PDDetailViewMeta} from "@/models/view/PDDetailViewMeta";

/**
 * Implementation of the generic detail view.
 * @author Kevin Link
 */
export default defineComponent({
  name: "DetailView",
  inject: ["currentLocale"],
  props: {
    context: {
      type: Object as PropType<PDDetailViewMeta>,
      required: true
    },
    creationMode: {
      type: Boolean,
      required: false
    }
  },
  setup() {
    const appStore = useApplicationStore();
    const route = useRoute();

    return {
      application: appStore.app,
      appStore,
      route
    }
  },
  data() {
    return {
      detailViewContext: null as DetailViewContext | null,
      entity: null as GenericEntity | null,
      refreshKey: 0,
      isSaving: false,
      isDeleting: false
    }
  },
  methods: {
    refresh: function () {
      this.refreshKey++;
    },
    saveEntity: async function () {
      this.isSaving = true;

      const {valid} = await this.$refs.form.validate();

      if (!valid) {
        this.isSaving = false;
        this.appStore.error(this.$i18n.t("messages.formInvalid"));
        return;
      }

      this.detailViewContext.associationStates.forEach((associationState) => {
        associationState.mergeChanges();
      });

      let result;
      if (this.creationMode) {
        result = await this.createEntity();
      } else {
        result = await this.updateEntity();
      }

      this.detailViewContext.entity = result;
      this.entity = result;

      if (result instanceof GenericEntity) {
        for (const [key, value] of this.detailViewContext.files.entries()) {
          if (value === null) {
            await this.deleteFile(result, key);
          } else {
            await this.uploadFile(result, key, value);
          }
        }

        if (this.creationMode) {
          this.$router.push({name: this.detailViewContext.entity.pdTypeMeta.name, params: {id: result.id}});
        }
      }
      this.refresh();
    },
    createEntity: function () {
      return GenericService.create(this.detailViewContext.entity, this.detailViewContext.associationStates)
          .then(json => {
            this.appStore.success(this.$i18n.t("messages.created", {
              entity: this.detailViewContext.detailViewMeta.type.label.labels[this.currentLocale]
            }));

            return new GenericEntity(json.typeId, json.id, json.properties, this.detailViewContext.detailViewMeta.type);
          }).finally(() => this.isSaving = false);
    },
    updateEntity: function () {
      return GenericService.update(this.detailViewContext.entity, this.detailViewContext.associationStates)
          .then(json => {
            this.appStore.success(this.$i18n.t("messages.updated", {
              entity: this.detailViewContext.detailViewMeta.type.label.labels[this.currentLocale]
            }));

            return new GenericEntity(json.typeId, json.id, json.properties, this.detailViewContext.detailViewMeta.type);
          }).finally(() => this.isSaving = false);
    },
    deleteEntity: async function () {
      this.isDeleting = true;
      const promise = await GenericService.delete(this.detailViewContext.entity).finally(() => this.isDeleting = false);

      if (promise === true) {
        this.appStore.success(this.$i18n.t("messages.deleted", {entity: this.detailViewContext.detailViewMeta.type.label.labels[this.currentLocale]}));
        this.toListView();
      }
    },
    fetchEntity: async function (type: PDTypeMeta) {
      if (this.creationMode) {
        this.detailViewContext.entity = new GenericEntity(type?.typeId, null, {}, type);
        this.entity = this.detailViewContext.entity;
        return;
      }

      await GenericService.fetch(type.typeId, Number(this.$route.params.id), [])
          .then(json => {
            this.detailViewContext.entity = new GenericEntity(json.typeId, json.id, json.properties, type);
            this.entity = this.detailViewContext.entity;
          });
    },
    toListView: function () {
      this.$router.push({name: this.detailViewContext.entity.pdTypeMeta.name + 's'});
    },
    uploadFile: async function (entity: GenericEntity, key: String, file: File) {
      let formData: FormData = FileService.buildFilePayload(
          entity.typeId,
          entity.id,
          key,
          file
      );

      return FileService.upload(formData)
          .then((result) => result.text())
          .then((text) => this.entity.properties[key] = text);
    },
    deleteFile: async function (entity: GenericEntity, key: String) {
      return FileService.delete(entity.typeId, entity.id, key);
    }
  },
  watch: {
    "context.detailViewMeta.type": async function (newType: PDTypeMeta) {
      this.entity = null;
      await this.fetchEntity(newType);
    }
  },
  async created() {
    this.detailViewContext = new DetailViewContext(this.context);

    const type = this.detailViewContext.detailViewMeta.type;
    await this.fetchEntity(type);
  }
});
</script>
