import { createApp } from 'vue'
import App from './App.vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/es/locale/lang/zh-cn'

// import './assets/main.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue' // 引入el-icon
import router from './router'

const app = createApp(App)

for (const [key, component] of Object.entries(ElementPlusIconsVue)) { // 使用el-icon
    app.component(key, component)
  }
app.use(ElementPlus,{locale:zhCn})
app.use(router)
app.mount('#app')
