<template>
  <v-app background="grey lighten-4">
    <v-content>
      <Navbar />
     <router-view />
    </v-content>
  </v-app>
</template>

<script>
import Navbar from '@/components/Navbar'
export default {
  name: 'App',

  components: {
    Navbar
  },

  data: () => ({
    //
  }),
};
</script>
<style>
</style>
