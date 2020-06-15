<template>
<div id="navbar">
<v-app-bar
    color="teal"
      data-aos="fade-down" data-aos-duration="1000">
    <v-toolbar color="teal" max-height="64px" extended elevation=0 data-aos="fade-down" data-aos-duration="1000">
      <v-app-bar-nav-icon @click="drawer = !drawer" class="hidden-md-and-up"></v-app-bar-nav-icon>
    
    <v-toolbar-title class="headline font-weight-medium"><v-icon size=30>mdi-recycle</v-icon>RECYCLE</v-toolbar-title>
      <v-spacer></v-spacer>
      <div class="hidden-sm-and-down">
        <v-btn text to="/">Home</v-btn>

        <v-btn text to="/about">About us</v-btn>

        <v-menu icon bottom origin="center center" transition="scale-transition">
          <template v-slot:activator="{ on }">
            <v-btn icon v-on="on">
              <v-icon>mdi-account</v-icon>
            </v-btn>
          </template>
          <v-list color="teal">
            <v-list-item v-if="!loggedIn" to="/login">
              <v-list-item-title>Log in</v-list-item-title>
            </v-list-item>
            <v-list-item v-if="loggedIn" to="/profile">
          <v-list-item-title>Profile</v-list-item-title>
        </v-list-item>
        <v-list-item v-if="isAdmin && loggedIn" to="/admin">
          <v-list-item-title>Admin</v-list-item-title>
        </v-list-item>
            <v-list-item v-if="loggedIn" to="/logout">
              <v-list-item-title>Log out</v-list-item-title>
            </v-list-item>
            <v-list-item v-if="!loggedIn" to="/register">
              <v-list-item-title>Register</v-list-item-title>
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
          <v-list-item-title>Home</v-list-item-title>
        </v-list-item>
        <v-list-item to="/about">
          <v-list-item-title>About us</v-list-item-title>
        </v-list-item>
        <v-list-item v-if="!loggedIn" to="/login">
          <v-list-item-title>Log in</v-list-item-title>
        </v-list-item>
        <v-list-item v-if="loggedIn" to="/profile">
          <v-list-item-title>Profile</v-list-item-title>
        </v-list-item>
        <v-list-item v-if="isAdmin && loggedIn" to="/admin">
          <v-list-item-title>Admin</v-list-item-title>
        </v-list-item>
        <v-list-item v-if="loggedIn" to="/logout">
          <v-list-item-title>Log out</v-list-item-title>
        </v-list-item>
        <v-list-item v-if="!loggedIn" to="/register">
          <v-list-item-title>Register</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
</div>
</template>
<script>
export default {
  data() {
    return {
      drawer: false
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
  name: "Navbar"
};
</script>
<style scoped>
</style>
