import Vue from 'vue'
import VueRouter from 'vue-router'
import Index from '@/components/Index'
import Login from '@/components/Login'
import Register from '@/components/Register'
import Profile from '@/components/Profile'
import Logout from '@/components/Logout'
import About from '@/components/About'
import EditProfile from '@/components/EditProfile'
import Admin from '@/components/Admin'
import Users from '@/components/Users'
import Schedules from '@/components/Schedules'
import Vehicles from '@/components/Vehicles'
import {store} from '../store/store'

Vue.use(VueRouter)

  const routes = [
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
      path: '/about',
      name: 'About',
      component: About
    },
    {
      path: '/admin',
      name: 'Admin',
      component: Admin,
      children:[{
        path: 'users',
        name: 'Users',
        component: Users,
      },
      {
        path: 'schedules',
        name: 'Schedules',
        component: Schedules,
      },
      {
        path: 'vehicles',
        name: 'Vehicles',
        component: Vehicles,
        }],
      beforeEnter: (to, from, next) =>
      {
        if(store.getters.isAdmin) next()
        else
        {
          alert("Nemate pravo pristupa, niste admin!")
          next({name : 'Login'})
        } 
      }
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
    },
   
  ],

 router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
