import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import TMS from '@/components/TMS'

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
    }
  ]
})
