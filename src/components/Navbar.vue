<template>
<div id="navbar">
<v-app-bar
    color="teal"
      data-aos="fade-down" data-aos-duration="1000">
    <v-toolbar color="teal" max-height="64px" extended elevation=0 data-aos="fade-down" data-aos-duration="1000">
      <v-app-bar-nav-icon @click="drawer = !drawer" class="hidden-md-and-up"></v-app-bar-nav-icon>
    
    <v-toolbar-title class="headline font-weight-medium"><v-icon size=30>mdi-recycle</v-icon>{{$t('navbar.title')}}</v-toolbar-title>
      <v-spacer></v-spacer>
      <div class="hidden-sm-and-down">
        <v-btn text to="/">{{$t('navbar.home')}}</v-btn>

        <v-btn text to="/about">{{$t('navbar.about')}}</v-btn>

       <v-btn v-if="loggedIn" text to="/announcements">{{$t('navbar.announcements')}}</v-btn>


        <v-menu icon bottom :offset-x="false" :offset-y="true" transition="scale-transition">
          <template v-slot:activator="{ on }">
            <v-btn icon v-on="on">
              <v-icon>mdi-account</v-icon>
            </v-btn>
          </template>
          <v-list color="teal">
            <v-list-item v-if="!loggedIn" to="/login">
              <v-list-item-title>{{$t('navbar.login')}}</v-list-item-title>
            </v-list-item>
            <v-list-item v-if="loggedIn" to="/profile">
          <v-list-item-title>{{$t('navbar.profile')}}</v-list-item-title>
        </v-list-item>
        <v-list-item v-if="isAdmin && loggedIn" to="/admin">
          <v-list-item-title>{{$t('navbar.admin')}}</v-list-item-title>
        </v-list-item>
            <v-list-item v-if="loggedIn" to="/logout">
              <v-list-item-title>{{$t('navbar.logout')}}</v-list-item-title>
            </v-list-item>
            <v-list-item v-if="!loggedIn" to="/register">
              <v-list-item-title>{{$t('navbar.register')}}</v-list-item-title>
            </v-list-item>
          </v-list>
        </v-menu>

          <v-menu icon bottom :offset-x="false" :offset-y="true" transition="scale-transition">
            <template v-slot:activator="{ on }">
            <v-btn icon v-on="on">
              <v-icon>mdi-flag</v-icon>
            </v-btn>
          </template>
          <v-list color="teal">
            <v-list-item v-for="lang in langs" :key="lang.value" style="cursor:pointer">
              <v-list-item-title v-text="lang.text" @click="changeLocale(lang.value)"></v-list-item-title>
            </v-list-item>
          </v-list>
         </v-menu>

      </div>
    </v-toolbar>
    </v-app-bar>
    <v-navigation-drawer color="teal" v-model="drawer" absolute class="hidden-md-and-up">
      <v-app-bar-nav-icon @click="drawer = !drawer"></v-app-bar-nav-icon>
      <v-list>
        <v-list-item to="/">
          <v-list-item-title>{{$t('navbar.home')}}</v-list-item-title>
        </v-list-item>
        <v-list-item to="/about">
          <v-list-item-title>{{$t('navbar.about')}}</v-list-item-title>
        </v-list-item>
         <v-list-item v-if="!loggedIn" to="/announcements">
          <v-list-item-title>{{$t('navbar.announcements')}}</v-list-item-title>
        </v-list-item>
        <v-list-item v-if="!loggedIn" to="/login">
          <v-list-item-title>{{$t('navbar.login')}}</v-list-item-title>
        </v-list-item>
        <v-list-item v-if="loggedIn" to="/profile">
          <v-list-item-title>{{$t('navbar.profile')}}</v-list-item-title>
        </v-list-item>
        <v-list-item v-if="isAdmin && loggedIn" to="/admin">
          <v-list-item-title>{{$t('navbar.admin')}}</v-list-item-title>
        </v-list-item>
        <v-list-item v-if="loggedIn" to="/logout">
          <v-list-item-title>{{$t('navbar.logout')}}</v-list-item-title>
        </v-list-item>
        <v-list-item v-if="!loggedIn" to="/register">
          <v-list-item-title>{{$t('navbar.register')}}</v-list-item-title>
        </v-list-item>
      </v-list>
        <v-menu icon bottom :offset-x="false" :offset-y="true" transition="scale-transition">
            <template v-slot:activator="{ on }">
            <v-btn icon v-on="on">
              <v-icon>mdi-flag</v-icon>
            </v-btn>
          </template>
          <v-list color="teal">
            <v-list-item v-for="lang in langs" :key="lang.value">
              <v-list-item-title v-text="lang.text" @click="$i18n.locale = lang.value"></v-list-item-title>
            </v-list-item>
          </v-list>
         </v-menu>
    </v-navigation-drawer>
</div>
</template>
<script>
export default {
  data() {
    return {
      drawer: false,
      langs: [
        {value: 'hr', text: this.$t('navbar.croatian')}, 
        {value: 'en', text: this.$t('navbar.english')} 
    ] 
    };
  },
  computed: {
    loggedIn() {
      return this.$store.getters.loggedIn;
    },
    isAdmin()
    {
      if(this.$store.state.profile != null)
      {
     return this.$store.getters.isAdmin
      }
      return false;
    }
  },
  methods:
  {
    changeLocale(locale)
    {
      this.$i18n.locale = locale; 
      this.$moment.locale(locale)
      localStorage.setItem('locale', locale)
     // this.$router.go()
    }
  },
  name: "Navbar"
};
</script>
<style scoped>
</style>
