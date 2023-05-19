import {defineStore} from "pinia";
import {Notification} from "@/models/Notification";
import {NotificationTypeET} from "@/models/NotificationTypeET";
import type {Application} from "@/models/runtime/Application";

/**
 * Pinia store to hold the state of the application and manage notifications.
 * @author Kevin Link
 */
export const useApplicationStore = defineStore("application", {
    state: () => ({
        app: null as Application | null,
        notifications: [] as Array<Notification>
    }),
    actions: {
        notify: function (level: NotificationTypeET, message: string) {
            this.notifications.unshift(new Notification(level, message));
            document.getElementById('app')?.scrollIntoView({behavior: "smooth"});
        },
        success: function (message: string) {
            this.notify(NotificationTypeET.SUCCESS, message);
        },
        info: function (message: string) {
            this.notify(NotificationTypeET.INFO, message);
        },
        warn: function (message: string) {
            this.notify(NotificationTypeET.WARN, message);
        },
        error: function (message: string) {
            this.notify(NotificationTypeET.ERROR, message);
        },
    }
});
