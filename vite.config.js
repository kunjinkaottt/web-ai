import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  server: {
    proxy: { //设置代理，必须填
        '/api': { //设置拦截器  拦截器格式   斜杠+拦截器名字，名字可以自己定
            target: 'http://localhost:8080',     //代理的目标地址
            secure: false,
            changeOrigin: true,              //是否设置同源，输入是的
            rewrite: (path) => path.replace(/^\/api/, ''),   //路径重写 以/api开头的接口，把/api去掉
        }
    }
}
})
