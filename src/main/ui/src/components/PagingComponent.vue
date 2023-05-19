<template>
  <v-row>
    <v-col cols="12" xl="1" lg="2" sm="4">
      <v-select
          :label="$t('listView.paging.pageSize')"
          :items="[5, 15, 25, 50, 100]"
          variant="plain"
          v-model="pageSizeProp"
          density="comfortable"
          :hide-details="true"
      ></v-select>
    </v-col>
    <v-col cols="12" xl="11" lg="10" sm="8" class="d-flex justify-end">
      <v-pagination
          v-model="pageProp"
          :length="totalPages"
          :total-visible="visiblePages"
          density="comfortable"
      ></v-pagination>
    </v-col>
  </v-row>
</template>

<script lang="ts">
import {defineComponent} from "vue";

/**
 * Component to allow navigation between pages of table result.
 * @author Kevin Link
 */
export default defineComponent({
  name: "PagingComponent",
  inject: ['currentLocale'],
  setup() {
    const visiblePages = 5;

    return {
      visiblePages
    }
  },
  props: {
    page: {
      type: Number,
      required: true,
      default: 1
    },
    pageSize: {
      type: Number,
      required: true,
      default: 15
    },
    totalPages: {
      type: Number,
      required: false,
      default: 1
    }
  },
  emits: ['update:page', 'update:page-size', 'change'],
  data() {
    return {}
  },
  computed: {
    pageProp: {
      // Proxy model to take and update parent model value
      get() {
        return this.page
      },
      set(newValue: string | number) {
        this.$emit('update:page', newValue);
        this.$emit('change');
      }
    },
    pageSizeProp: {
      // Proxy model to take and update parent model value
      get() {
        return this.pageSize
      },
      set(newValue: string | number) {
        this.$emit('update:page-size', newValue);
        this.$emit('change');
      }
    }
  },
})
</script>