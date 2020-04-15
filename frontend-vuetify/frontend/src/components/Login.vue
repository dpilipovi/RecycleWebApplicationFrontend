<template>
<div id="app">
  <v-app id="inspire">
    <v-content>
      <v-container
        fluid
        fill-height
      >
        <v-layout
          align-center
          justify-center
        >
          <v-flex
            xs12
            sm8
            md4
          >
            <v-card class="elevation-12">
              <v-toolbar
                color="teal"
                dark
                flat
              >
                <v-toolbar-title>Login form</v-toolbar-title>
                <v-spacer></v-spacer>
              </v-toolbar>
              <v-card-text>
                <v-form>
                  <v-text-field
                    label="Username"
                    name="username"
                    prepend-icon="person"
                    type="text"
                    v-model="username"
                  ></v-text-field>

                  <v-text-field
                    id="password"
                    label="Password"
                    name="password"
                    prepend-icon="lock"
                    type="password"
                    v-model="password"
                  ></v-text-field>
                </v-form>
              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="teal" dark @click="login()">Login </v-btn>
              </v-card-actions>
            </v-card>
          </v-flex>
        </v-layout>
      </v-container>
    </v-content>
  </v-app>
</div>
<!-- <div class="hero-body is-desktop is-vcentered">
        <div class="column is-4 is-offset-4">
           <div class="field">
           <p class="control has-icons-left has-icons-right">
             <input class="input" type="text" placeholder="Username" v-model="username"> 
             <span class="icon is-small is-left">
               <i class="fa fa-user"></i>
             </span>
             <span class="icon is-small is-right">
               <i class="fa fa-check"></i> 
             </span>
           </p>
         </div>
         <div class="field">
           <p class="control has-icons-left">
             <input class="input" type="password" placeholder="Lozinka" v-model="pass">
             <span class="icon is-small is-left">
               <i class="fa fa-lock"></i>
             </span>
           </p>
         </div>
         <div class="field">
           <p class="control">
             <button class="button is-info" @click="login()" :disabled="!isComplete">
               Login
             </button>
           </p>
         </div>
       </div>
    </div> -->
</template>
<script>
export default {
      data() {
      return {
       username: '',
       password: ''
      }
    },
    mounted()
    {
      if (localStorage.getItem('redirected')) 
      {
        localStorage.removeItem('redirected');
        location.reload();  
    } else 
    {
        // Set a flag so that we know not to reload the page twice.
        localStorage.setItem('redirected', '1');
    }
    },
    methods: {
      login(){
      
      if(!this.username || !this.password) return
      this.data=
      {
        username: this.username,
        password: this.password

      }

      this.$store.dispatch('setToken', this.data)
      .then(response =>
      {
        console.log(response)
          this.$router.push('/');
      })
    
      }
    },
  name: 'Login'
}
</script>
<style scoped>
.input
{
  background:rgba(0, 0, 0, 0.6);
  color:white;
}
::placeholder
{
  color:gray;
}
</style>
