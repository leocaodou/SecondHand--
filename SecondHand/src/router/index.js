import Vue from 'vue'
import Router from 'vue-router'
import menuweb from '@/components/menu'
import login from '@/components/login.vue'
import register from '@/components/register.vue'
import goods from '@/components/goods.vue'
import cart from '@/components/cart.vue'
import stu from '@/components/stu.vue'
import list from '@/components/list.vue'
import sell from '@/components/sell.vue'
import test from '@/components/test.vue'
import admin from '@/components/admin.vue'
import administ from '@/components/administ.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      redirect: '/menu'
    },
    {
      path: '/menu',
      name: 'menuweb',
      redirect: '/menu/goods',
      component: menuweb,
      children: [
        {
          path: 'goods',
          name: 'goods',
          component: goods,
        },
        {
          path: 'cart',
          name: 'cart',
          component: cart,
        },
        {
          path: 'stu',
          name: 'stu',
          component: stu,
          redirect: '/menu/stu/list',
          children:[
            {
              path: 'list',
              name: 'list',
              component:list,
            },
            {
              path: 'sell',
              name: 'sell',
              component:sell,
            }
          ]
        }
      ]
    },
    {
      path: '/login',
      name: 'login',
      component: login
    },
    {
      path: '/register',
      name: 'register',
      component: register
    },
    {
      path: '/admin',
      name: 'admin',
      component: admin
    },
    {
      path: '/administ',
      name: 'administ',
      component: administ
    },
    {
      path: '/test',
      name: 'test',
      component: test
    }
  ]
})
