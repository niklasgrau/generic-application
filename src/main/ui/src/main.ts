import * as components from "vuetify/components";
import * as directives from "vuetify/directives";
import {computed, createApp} from "vue";
import {createVuetify} from "vuetify";
import {createPinia} from "pinia";

import App from "./App.vue";
import router from "./router";

import '@mdi/font/css/materialdesignicons.css'
import "vuetify/styles";
import {aliases, mdi} from "vuetify/iconsets/mdi"
import i18n, {getLocale} from "@/i18n";
import Runtime from "@/Runtime";
import ColumnComponent from "@/components/uiFactories/ColumnComponent.vue";
// Ui Factories
// # Groups
import RowGroup from "@/components/uiFactories/groups/RowGroup.vue";
import ColumnGroup from "@/components/uiFactories/groups/ColumnGroup.vue";
import CardGroup from "@/components/uiFactories/groups/CardGroup.vue";
import CollapsableGroup from "@/components/uiFactories/groups/CollapsableGroup.vue";
import TabGroup from "@/components/uiFactories/groups/TabGroup.vue";
import TabContainerGroup from "@/components/uiFactories/groups/TabContainerGroup.vue";

// # Items
import TextInput from "@/components/uiFactories/items/controls/TextInput.vue";
import PasswordInput from "@/components/uiFactories/items/controls/PasswordInput.vue";
import MultilineTextInput from "@/components/uiFactories/items/controls/MultilineTextInput.vue";
import CurrencyNumberInput from "@/components/uiFactories/items/controls/CurrencyNumberInput.vue";
import NumberInput from "@/components/uiFactories/items/controls/NumberInput.vue";
import DateInput from "@/components/uiFactories/items/controls/DateInput.vue";
import DateTimeInput from "@/components/uiFactories/items/controls/DateTimeInput.vue";
import ToggleInput from "@/components/uiFactories/items/controls/ToggleInput.vue";
import EnumInput from "@/components/uiFactories/items/controls/EnumInput.vue";
import FileInput from "@/components/uiFactories/items/controls/FileInput.vue";
import FilePreview from "@/components/uiFactories/items/controls/FilePreview.vue";
import ToManyTableAssociation from "@/components/uiFactories/items/associations/ToManyTableAssociation.vue";
import ToManySelectAssociation from "@/components/uiFactories/items/associations/ToManySelectAssociation.vue";
import ToOneAssociation from "@/components/uiFactories/items/associations/ToOneAssociation.vue";
import AutocompleteAssociation from "@/components/uiFactories/items/associations/AutocompleteAssociation.vue";

// Formatter Factories
import NumberFormatter from "@/components/formatterFactories/NumberFormatter.vue";
import DateFormatter from "@/components/formatterFactories/DateFormatter.vue";
import BooleanFormatter from "@/components/formatterFactories/BooleanFormatter.vue";
import EMailFormatter from "@/components/formatterFactories/EMailFormatter.vue";
import EnumFormatter from "@/components/formatterFactories/EnumFormatter.vue";

let app = createApp(App);

const vuetify = createVuetify({
    components,
    directives,
    icons: {
        defaultSet: 'mdi',
        aliases,
        sets: {
            mdi
        },
    }
});

// Registering Components
app.component("ColumnComponent", ColumnComponent);

// Ui Factories
// # Groups
app.component("RowGroup", RowGroup);
app.component("ColumnGroup", ColumnGroup);
app.component("TabContainerGroup", TabContainerGroup);
app.component("TabGroup", TabGroup);
app.component("CollapsableGroup", CollapsableGroup);
app.component("CardGroup", CardGroup);
// # Items
app.component("TextInput", TextInput);
app.component("MultilineTextInput", MultilineTextInput);
app.component("PasswordInput", PasswordInput);
app.component("NumberInput", NumberInput);
app.component("CurrencyNumberInput", CurrencyNumberInput);
app.component("DateInput", DateInput);
app.component("DateTimeInput", DateTimeInput);
app.component("ToggleInput", ToggleInput);
app.component("FileInput", FileInput);
app.component("FilePreview", FilePreview);
app.component("EnumInput", EnumInput);
app.component("ToManyTableAssociation", ToManyTableAssociation);
app.component("ToManySelectAssociation", ToManySelectAssociation);
app.component("ToOneAssociation", ToOneAssociation);
app.component("AutocompleteAssociation", AutocompleteAssociation);

// Formatter Factories
app.component("NumberFormatter", NumberFormatter);
app.component("DateFormatter", DateFormatter);
app.component("BooleanFormatter", BooleanFormatter);
app.component("EMailFormatter", EMailFormatter);
app.component("EnumFormatter", EnumFormatter);

app.use(createPinia());
app.use(vuetify);
app.use(i18n);

app.provide("currentLocale", computed(() => getLocale()));

app.config.unwrapInjectedRef = true;
// The above usage requires setting app.config.unwrapInjectedRef = true
// to make injections automatically unwrap computed refs. This will become
// the default behavior in Vue 3.3 and this config is introduced temporarily to avoid breakage.
// It will no longer be required after 3.3.

await Runtime.initialize();

// Register Vue router plugin
app.use(router);

// Mount app (start application)
app.mount("#app");