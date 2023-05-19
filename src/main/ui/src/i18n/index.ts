import {createI18n} from "vue-i18n";

import en from "./locales/en.json";
import de from "./locales/de.json";

/**
 * Definition of the localization by i18n.
 * @author Kevin Link
 */
const i18n = createI18n({
    locale: 'de',
    fallbackLocale: 'en',
    legacy: true,
    messages: {
        de,
        en
    }
});

export function getLocale(): string {
    return i18n.global.locale.toUpperCase();
}

export function setLocale(newLocale: 'de' | 'en'): void {
    i18n.global.locale = newLocale;
}

export default i18n;