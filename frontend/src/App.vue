<template>
<section class="hero bg is-fullheight">
  <div id="app">
     <navbar/>
     <router-view/>
    <!-- <Footer/>-->
  </div>
 </section>
</template>
<script src="https://cdn.jsdelivr.net/npm/vue@2.6.11"></script>
<script>
import Navbar from '@/components/Navbar.vue'
import Footer from '@/components/Footer.vue'

export default {
  name: 'App',
  components: {
    Navbar,
    Footer
  }
}
</script>
<style scoped>
.bg {
 /* background: url(assets/images/background7.jpg) top left no-repeat;*/
  background: url(assets/images/profile_background.jpeg) top left no-repeat;
  background-size: cover;
}
footer {
   position: absolute;
  bottom: 0;
  width: 100%;
  height: 50px;
  background:rgba(0,0,0,0.2);
}

</style>
