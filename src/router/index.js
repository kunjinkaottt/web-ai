import {createRouter , createWebHistory} from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
        path: '/',
        name: 'Layout',
        component: () => import('@/views/layout/index.vue'),
        redirect: '/home', // 重定向    uri是/时默认访问/home
        children:[
            {
                path: 'home', // path拼接结果就是 /home
                name: 'Home',
                component: () => import('@/views/home/index.vue')
            },
            {
              path: 'emp',
              name: 'Emp',
              component: () => import('@/views/emp/index.vue')
            },
            {
                path: 'dept',
                name: 'Dept',
                component: () => import('@/views/dept/index.vue')
            },
            {
                path: 'stu',
                name: 'Stu',
                component: () => import('@/views/stu/index.vue')
            },
             {
              path: 'clazz',
              name: 'Clazz',
              component: () => import('@/views/clazz/index.vue')
            },
            {
                path: 'empReport',
                name: 'EmpReport',
                component: () => import('@/views/report/empReport.vue')
            },
            {
                path: 'stuReport',
                name: 'StuReport',
                component: () => import('@/views/report/stuReport.vue')
            },
        ]
    },
      {
        path: '/login',
        name: 'Login',
        component: () => import('@/views/login/index.vue')
      }
  ]
})

export default router
