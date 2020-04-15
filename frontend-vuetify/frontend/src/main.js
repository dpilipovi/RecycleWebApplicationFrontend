import Vue from 'vue'
import App from './App.vue'
import router from './router'
import {store} from './store/store'
import vuetify from './plugins/vuetify';
import AOS from "aos";
import "aos/dist/aos.css";
import 'material-design-icons-iconfont/dist/material-design-icons.css'

Vue.config.productionTip = false

new Vue({
  created() {
    AOS.init({ once: true});
  },
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')
