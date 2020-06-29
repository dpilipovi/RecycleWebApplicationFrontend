import Vue from 'vue'
import App from './App.vue'
import router from './router'
import { store } from './store/store'
import vuetify from './plugins/vuetify';
import AOS from "aos";
import "aos/dist/aos.css";
import * as VueGoogleMaps from "vue2-google-maps";
import VideoBackground from 'vue-responsive-video-background-player'
import VueVideoSection from 'vue-video-section'
import i18n from './i18n'
import moment from 'moment-timezone'
import VueMoment from 'vue-moment'
import VueApexCharts from 'vue-apexcharts'




Vue.use(VueMoment, {
  moment,
})


Vue.use(VueApexCharts)

Vue.component('apexchart', VueApexCharts)

Vue.component('vue-video-section', VueVideoSection)

Vue.component('video-background', VideoBackground);


Vue.use(VueGoogleMaps, {
  load: {
    key: "AIzaSyAIFsjNZOTpPJpJ9rQei3Y8F2gAJ6OQoR4",
    libraries: 'places', // necessary for places input
  }
});



Vue.config.productionTip = false

new Vue({
  created() {
    AOS.init({ once: true });
  },

  router,
  store,
  vuetify,
  i18n,
  render: h => h(App)
}).$mount('#app')
