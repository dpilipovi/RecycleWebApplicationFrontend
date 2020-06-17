import Vue from 'vue'
import App from './App.vue'
import router from './router'
import {store} from './store/store'
import vuetify from './plugins/vuetify';
import AOS from "aos";
import "aos/dist/aos.css";
import * as VueGoogleMaps from "vue2-google-maps";
import VideoBackground from 'vue-responsive-video-background-player'
import VueVideoSection from 'vue-video-section'


Vue.component('vue-video-section', VueVideoSection)

Vue.component('video-background', VideoBackground);


Vue.use(VueGoogleMaps, {
  load: {
    key: "AIzaSyAIFsjNZOTpPJpJ9rQei3Y8F2gAJ6OQoR4",
    libraries: 'places', // necessary for places input
  }
});

//Vue.component('video-bg', VideoBg)

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
