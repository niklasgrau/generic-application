<template>
  <v-breadcrumbs :items="crumbs">
    <template v-slot:prepend>
      <v-icon size="small" icon="mdi-home"></v-icon>
    </template>
    <template v-slot:title="{ item }">
      {{ item.title }}
    </template>
  </v-breadcrumbs>
</template>

<script lang="ts">
import {defineComponent, type PropType} from "vue";
import type {PDApplication} from "@/models/core/PDApplication";
import {BreadcrumbItemTypeET} from "@/models/BreadcrumbItemTypeET";
import type {BreadcrumbItem} from "@/models/BreadcrumbItem";

/**
 * Component representing bread crumb menu for navigation.
 * @author Kevin Link
 */
export default defineComponent({
  name: "BreadcrumbMenu",
  inject: ["currentLocale"],
  props: {
    context: {
      type: Object as PropType<PDApplication>,
      required: true
    }
  },
  computed: {
    crumbs: function () {
      // Initialize crumbs with application root
      const crumbs = [
        {
          title: this.context.label.labels[this.currentLocale],
          disabled: false,
          to: '/',
        }
      ];

      if (!this.$route.meta.breadcrumb || (<Array<BreadcrumbItem>>this.$route.meta.breadcrumb).length === 0) {
        return crumbs;
      }

      for (const breadcrumbItem of (<Array<BreadcrumbItem>>this.$route.meta.breadcrumb)) {
        let {title, disabled, to, type, contextTitle} = breadcrumbItem;

        let crumbTitle = null;

        switch (type) {
          case BreadcrumbItemTypeET.DETAIL_VIEW:
            if (this.$route.params.id) {
              crumbTitle = `#${this.$route.params.id}`;
              to = to.replace(':id', this.$route.params.id);
              break;
            }
            crumbTitle = (typeof title === 'string') ? this.$i18n.t(title) : title?.labels[this.currentLocale];
            break;
          case BreadcrumbItemTypeET.CREATE_VIEW:
            crumbTitle = (typeof title === 'string') ? this.$i18n.t(title, {entity: contextTitle?.labels[this.currentLocale]}) : title?.labels[this.currentLocale];
            break;
          default:
            crumbTitle = (typeof title === 'string') ? this.$i18n.t(title) : title?.labels[this.currentLocale];
        }

        crumbs.push({
          title: crumbTitle,
          disabled: disabled,
          to: to
        });
      }


      // if (this.$route.meta.subsystem) {
      //   crumbs.push({
      //     title: this.$route.meta.subsystem.label.getLabel(this.currentLocale),
      //     disabled: true,
      //     to: ""
      //   });
      // }
      //
      // if (this.$route.meta.title && (this.$route.meta.title instanceof MultiLanguageLabel)) {
      //   crumbs.push({
      //     title: this.$route.meta.title.getLabel(this.currentLocale),
      //     disabled: false,
      //     to: this.$route.path
      //   });
      // } else if (this.$route.meta.title) {
      //   crumbs.push({
      //     title: this.$i18n.t(this.$route.meta.title),
      //     disabled: false,
      //     to: this.$route.path
      //   });
      // }
      //
      // if(this.$route.params.id) {
      //   crumbs.push({
      //     title: `#${this.$route.params.id}`,
      //     disabled: true,
      //     to: this.$route.path
      //   });
      // }

      return crumbs;
    }
  }
});
</script>
