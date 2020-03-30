<template>
      <div class="hero-body">
        <div class="column is-4 is-offset-4">
           <div class="field">
            <p class="control has-icons-left">
                <input class="input" type="text" placeholder="Ime"  v-model="user.username">
                <span class="icon is-small is-left">
                <i class="fa fa-user"></i>
                </span>
            </p>
            </div>
             <div class="field">
            <p class="control has-icons-left">
                <input class="input" type="text" placeholder="Prezime" v-model="user.firstname">
                <span class="icon is-small is-left">
                <i class="fa fa-user"></i>
                </span>
            </p>
            </div>
            <div class="field">
            <p class="control has-icons-left">
                <input class="input" type="text" placeholder="Korisničko ime"  v-model="user.lastname">
                <span class="icon is-small is-left">
                <i class="fa fa-user"></i>
                </span>
            </p>
            </div>
           <div class="field">
           <p class="control has-icons-left has-icons-right">
             <input class="input" type="email" placeholder="Email"  v-model="user.email">
             <span class="icon is-small is-left">
               <i class="fa fa-envelope"></i>
             </span>
           </p>
         </div>
         <div class="field">
            <p class="control has-icons-left">
              <input class="input" type="text" placeholder="Adresa"  v-model="user.address">
              <span class="icon is-small is-left">
                <i class="fa fa-home"></i>
              </span>
            </p>
          </div>
         <div class="field">
           <p class="control has-icons-left">
             <input class="input" type="password" placeholder="Nova Lozinka" v-model="user.password">
             <span class="icon is-small is-left">
               <i class="fa fa-lock"></i>
             </span>
           </p>
         </div>
         <div class="field">
            <p class="control has-icons-left">
              <input class="input" type="password" placeholder="Ponovi lozinku" v-model="pass2" >
              <span class="icon is-small is-left">
                <i class="fa fa-lock"></i>
              </span>
            </p>
          </div>
         <div class="field">
           <p class="control">
              <router-link class="button is-danger " to="/profile">Back</router-link>
             <button class="button is-warning" @click="updateProfile()" :disabled="!isComplete">
               Update
             </button>
           </p>
         </div>
       </div>       
    </div>
    
</template>
<script>
export default
{
    data()
    {
        return{
             profile: this.$store.state.profile,
             user : {
             id : null,
             password : "",
             firstname : "",
             lastname : "",
             username : "",
             email : "",
             address : "",
             level : null
             },
             pass2 : ""
        }
       
    },
    created()
    {
      this.user.id=this.profile.id;
      this.user.firstname=this.profile.firstname;
      this.user.lastname=this.profile.lastname;
      this.user.username=this.profile.username;
      this.user.email=this.profile.email;
      this.user.address=this.profile.address;
      this.user.level=this.profile.level;

    },
    
    computed: {
      isComplete () {
      if(this.profile.username != "" && this.user.name != "" && this.user.lastname != "" && this.user.email != "" && this.user.address != "" && this.user.password==this.pass2 ) return true;
      return false;
      }
    },

    methods: {

      updateProfile()
      {
        //console.log(this.user.password +" "+this.pass2 +" "+this.user.firstname +" "+this.user.lastname +" "+this.user.username +" "+this.user.email +" "+this.user.address);
        //console.log(this.$store.state.profile);
       let flag= false;
       if(this.pass2 != "") flag=true;
       let data=
        {
          user: this.user,
          flag: flag
        }

        this.$store.dispatch('editUser', data)
            .then(response =>
            {
              localStorage.setItem('redirected','1')
             /*if(this.user.password != "") */
             this.$router.push('/login');
            })

      }

    },
  name: 'EditProfile'
}
</script>
<style scoped>
.button
{
    color:white;
}
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
