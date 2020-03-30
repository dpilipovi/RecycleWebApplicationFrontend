<template>
    <div class="hero-head">
        <nav class="navbar">
            <div class="container">
                <div class="navbar-brand">
                    <a class="navbar-item has-text-white is-size-3 has-text-weight-bold" href="#">
                       RECYCLE
                    </a>
                    <span class="navbar-burger burger has-text-white" role="button" tabindex="0" data-target="navbar-menu">
                        <span ></span>
                        <span ></span>
                        <span ></span>
                    </span>
                </div>
                <div  id="navbar-menu" class="navbar-menu">
                    <div class="navbar-end">
                        <span class="navbar-item">
                            <router-link class="button is-white is-outlined" to="/">
                                <span class="icon">
                                    <i class="fa fa-home"></i>
                                </span>
                                <span>Početna</span>
                            </router-link>
                        </span>
                        <span class="navbar-item">
                            <router-link class="button is-white is-outlined" to="/contact">
                                <span class="icon">
                                    <i class="fa fa-exclamation"></i>
                                </span>
                                <span>Kontakt</span>
                            </router-link>
                        </span>
                        <span class="navbar-item"  v-if="!loggedIn">
                          <router-link class="button is-white is-outlined" to="/login">
                            <span class="icon">
                              <i class="fa fa-sign-in"></i>
                                </span>
                                <span>Prijavi se</span>
                            </router-link>
                        </span>
                        <span class="navbar-item" v-if="!loggedIn">
                          <router-link class="button is-white is-outlined" to="/register">
                            <span class="icon">
                                <i class="fa fa-user"></i>
                                </span>
                                <span>Registriraj se</span>
                            </router-link>
                        </span>
                           <span class="navbar-item"  v-if="loggedIn">
                            <router-link class="button is-white is-outlined" to="/profile">
                                <span class="icon">
                                    <i class="fa fa-user"></i>
                                </span>
                                <span>Profil</span>
                            </router-link>
                        </span>
                           <span class="navbar-item" v-if="loggedIn">
                            <router-link class="button is-white is-outlined" to="/logout">
                                <span class="icon">
                                    <i class="fa fa-user"></i>
                                </span>
                                <span>Odjavi se</span>
                            </router-link>
                        </span>
                    </div>
                </div>
            </div>
        </nav>
        </div>
</template>

<script>
/* JQUERY
$(document).ready(function() {

  // Check for click events on the navbar burger icon
  $(".navbar-burger").click(function() {

      // Toggle the "is-active" class on both the "navbar-burger" and the "navbar-menu"
      $(".navbar-burger").toggleClass("is-active");
      $(".navbar-menu").toggleClass("is-active");

  });
});
*/   
//js vanilla
document.addEventListener('DOMContentLoaded', () => {

  // Get all "navbar-burger" elements
  const $navbarBurgers = Array.prototype.slice.call(document.querySelectorAll('.navbar-burger'), 0);

  // Check if there are any navbar burgers
  if ($navbarBurgers.length > 0) {

    // Add a click event on each of them
    $navbarBurgers.forEach( el => {
      el.addEventListener('click', () => {

        // Get the target from the "data-target" attribute
        const target = el.dataset.target;
        const $target = document.getElementById(target);

        // Toggle the "is-active" class on both the "navbar-burger" and the "navbar-menu"
        el.classList.toggle('is-active');
        $target.classList.toggle('is-active');

      });
    });
  }

});

export default {
    computed:
    {
        loggedIn()
        {
            return this.$store.getters.loggedIn 
        }

    },
    name: 'Navbar'
}
</script>
<style scoped>
.hero-head{
  background:rgba(0,0,0,0.1);
}
.title
{
    color:white;
}

@media only screen and (max-width: 768px) {
  .navbar-end span  {
   color:black;
  }
}

</style>
