<template>
  <v-alert :color="color(message.type)" :icon="icon(message.type)" border :text="message.message.substring(0,255)" closable
           v-for="(message, index) in messages" density="compact" @close="close(index)" v-show="timeout(index)"
           :class="{ 'mb-6': index === messages.length - 1, 'mb-2': index !== messages.length - 1 }"/>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import {NotificationTypeET} from "@/models/NotificationTypeET";

/**
 * Component to show and manage notifications.
 * @author Kevin Link
 */
export default defineComponent({
  name: "NotificationComponent",
  props: {
    messages: {
      type: Array,
      required: false
    }
  },
  methods: {
    color: function (type: NotificationTypeET) {
      switch (type) {
        case NotificationTypeET.INFO:
          return "info";
        case NotificationTypeET.SUCCESS:
          return "success";
        case NotificationTypeET.WARN:
          return "warning"
        case NotificationTypeET.ERROR:
          return "error";
        default:
          return "default";
      }
    },
    icon: function (type: NotificationTypeET) {
      switch (type) {
        case NotificationTypeET.INFO:
          return "mdi-information-outline";
        case NotificationTypeET.SUCCESS:
          return "mdi-check-circle-outline";
        case NotificationTypeET.WARN:
          return "mdi-alert-outline";
        case NotificationTypeET.ERROR:
          return "mdi-alert-circle-outline";
        default:
          return "mdi-bell-outline";
      }
    },
    close: function (index: number) {
      this.messages?.splice(index, 1);
    },
    timeout: function (index: number) {
      setTimeout(() => this.close(index), 10000);
      return true;
    }
  }
})
</script>
