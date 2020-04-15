<template>
        <div class="hero-body">
        <div class="column is-4 is-offset-4">
           <div class="field">
            <p class="control has-icons-left">
                <input class="input" type="text" placeholder="Ime" v-model="name">
                <span class="icon is-small is-left">
                <i class="fa fa-user"></i>
                </span>
            </p>
            </div>
             <div class="field">
            <p class="control has-icons-left">
                <input class="input" type="text" placeholder="Prezime" v-model="lastname">
                <span class="icon is-small is-left">
                <i class="fa fa-user"></i>
                </span>
            </p>
            </div>
            <div class="field">
            <p class="control has-icons-left">
                <input class="input" type="text" placeholder="Korisničko ime" v-model="username">
                <span class="icon is-small is-left">
                <i class="fa fa-user"></i>
                </span>
            </p>
            </div>
           <div class="field">
           <p class="control has-icons-left has-icons-right">
             <input class="input" type="email" placeholder="Email" v-model="email">
             <span class="icon is-small is-left">
               <i class="fa fa-envelope"></i>
             </span>
           </p>
         </div>
         <div class="field">
            <p class="control has-icons-left">
              <input class="input" type="text" placeholder="Adresa" v-model="address">
              <span class="icon is-small is-left">
                <i class="fa fa-home"></i>
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
            <p class="control has-icons-left">
              <input class="input" type="password" placeholder="Ponovi lozinku" v-model="pass2">
              <span class="icon is-small is-left">
                <i class="fa fa-lock"></i>
              </span>
            </p>
          </div>
         <div class="field">
           <p class="control">
             <button class="button is-info" @click="register()" :disabled="!isComplete">
               Register
             </button>
           </p>
         </div>
       </div>       
    </div>
</template>
<script>
import http from "../http-common";

export default {

   data () {
    return {
      username: '',
      pass: '' ,
      email: '',
      address: '',
      pass2: '',
      name: '',
      lastname: ''
    }  
  },
    methods: {
      register(){
      this.data=
      {
        username: this.username,
        password: this.pass,
        firstname: this.name,
        lastname: this.lastname,
        email: this.email,
        address: this.address
        

      }
      http
        .post("/register", this.data)
        .then(response => {
          console.log(response.data);
          if(response.data==true) this.$router.push('/login');
        })
        .catch(e => {
          console.log(e);
        });
      }
    },
    computed: {
      isComplete () {
      if(this.username != "" && this.pass != "" && this.pass2 != "" && this.name != "" && this.lastname != "" && this.email != "" && this.address != "" && this.pass==this.pass2 ) return true;
      return false;
      }
    },
  name: 'Register'
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