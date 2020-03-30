import Vue from 'vue'
import Router from 'vue-router'
import Index from '@/components/Index'
import Login from '@/components/Login'
import Register from '@/components/Register'
import Profile from '@/components/Profile'
import Logout from '@/components/Logout'
import Contact from '@/components/Contact'
import EditProfile from '@/components/EditProfile'
import Admin from '@/components/Admin'
import {store} from '../store/store'



Vue.use(Router)

export default new Router({

  routes: [
    {
      path: '/',
      name: 'Index',
      component: Index
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/register',
      name: 'Register',
      component: Register
    },
    {
      path: '/logout',
      name: 'Logout',
      component: Logout
    },
    {
      path: '/contact',
      name: 'Contact',
      component: Contact
    },
    {
      path: '/admin',
      name: 'Admin',
      component: Admin
    },
    {
      path: '/profile',
      name: 'Profile',
      component: Profile,
      beforeEnter: (to, from, next) =>
      {
        if(store.getters.loggedIn) next()
        else
        {
          alert("Nemate pravo pristupa, molimo ulogirajte se!")
          next({name : 'Login'})
        } 
      }
    },
    {
      path: '/editProfile',
      name: 'editProfile',
      component: EditProfile,
      beforeEnter: (to, from, next) =>
      {
        if(store.getters.loggedIn) next()
        else
        {
          alert("Nemate pravo pristupa, molimo ulogirajte se!")
          next({name : 'Login'})
        } 
      }
    }
  ],
   mode: 'history'
})
