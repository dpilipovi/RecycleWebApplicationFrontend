<template>
<div class="hero-body is-desktop is-vcentered">
        <div class="column is-4 is-offset-4">
           <div class="field">
           <p class="control has-icons-left has-icons-right">
             <input class="input" type="text" placeholder="Username" v-model="username"> 
             <span class="icon is-small is-left">
               <i class="fa fa-user"></i>
             </span>
             <span class="icon is-small is-right">
               <i class="fa fa-check"></i> <!--MOGU PROVJERAVATI KAD JE UNOS DOBAR PA STAVIT KVACICE POSLIJE -->
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
    </div>
</template>
<script>
export default {
   data () {
    return {
      username: '',
      pass: ''
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
      this.data=
      {
        username: this.username,
        password: this.pass

      }

      this.$store.dispatch('setToken', this.data)
      .then(response =>
      {
        console.log(response)
          this.$router.push('/');
      })
    
      }
    },
    computed: {
      isComplete () {
       return this.username && this.pass;
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
