import {fileURLToPath, URL} from "node:url";

import {defineConfig} from "vite";
import {dirname, resolve} from 'node:path'
import vue from "@vitejs/plugin-vue";
import vueI18n from "@intlify/vite-plugin-vue-i18n";

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [
        vue(),
        vueI18n({
            // for Options API use false, for Composition API use true (default)
            compositionOnly: false,
            // paths to the localization files (path must be included)
            include: resolve(dirname(fileURLToPath(import.meta.url)), './src/i18n/locales/**'),
        })
    ],
    resolve: {
        alias: {
            "@": fileURLToPath(new URL("./src", import.meta.url)),
        },
    },
    server: {
        proxy: {
            // with options
            '/api': {
                target: 'http://localhost:8080',
                // changeOrigin: true,
                // ws: true,
                //rewrite: (path) => path.replace(/^\/api/, '')
            },
        }
    }
});
