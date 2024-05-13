import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { fileURLToPath, URL } from 'node:url'

const pathResolve = (dir: string) => fileURLToPath(new URL(dir, import.meta.url))

export default defineConfig({
    plugins: [vue()],
    resolve: {
        alias: {
            '@': pathResolve('./src'),
            '@assets': pathResolve('./src/assets'),
            '@components': pathResolve('./src/components'),
            '@config': pathResolve('./src/config'),
            '@plugins': pathResolve('./src/plugins'),
            '@router': pathResolve('./src/router'),
            '@services': pathResolve('./src/services'),
            '@stores': pathResolve('./src/stores'),
            '@views': pathResolve('./src/views')
        }
    }
})
