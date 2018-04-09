import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import TMS from '@/components/TMS'
import create from '@/components/create'
import home from '@/components/home'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/app',
      name: 'TMS',
      component: TMS
    },
    {
      path: '/create',
      name: 'create',
      component: create
    },
    {
      path: '/home',
      name: 'home',
      component: home
    }
  ]
})
