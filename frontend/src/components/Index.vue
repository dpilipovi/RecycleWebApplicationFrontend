<template>
        <div class="hero-body ">
            <div class="container has-text-centered">
                <div class="column is-6 is-offset-3">
                    <h1 class="title">
                        Kada se prikuplja smeće kod Vas?
                    </h1>
                        <div class="field is-grouped">
                            <p class="control is-expanded">
                                <input class="input" type="text" placeholder="Unesite ime svoje ulice" v-model="selectedAddress">
                            </p>
                            <p class="control">
                                <a class="button is-info" :disabled="!isComplete" @click="showAddress">
                                    Submit
                                </a>
                            </p>
                        </div>
                </div>
                <div class="center">
                 <div class="red" :hidden="!error"> <p>Zadana adresa ne postoji!</p></div>
                  <table class = "table" :hidden="hide">
               <thead>
                  <tr>
                     <th>Vrsta Otpada</th>
                     <th>Dan</th>
                  </tr>
               </thead>
               <tbody>
                  <tr v-for="(r, index) in raspored" :key="index">
                     <td>{{r.type}}</td>
                     <td>{{r.day}}</td>
                  </tr>
               </tbody>
            </table>      
              </div>
            </div>
        </div>
        
</template>
  <script>
  import http from "../http-common";     

        
  export default {
    data() {
      return {
        raspored: [],
        selectedAddress: '',
        hide: true,
        error: false
      }
    },
   mounted() {

     if(this.$store.getters.loggedIn==true) {
       this.$store.dispatch('setProfile')
        .then()
        {
           // console.log("proslo")
        }
     }
    if (localStorage.getItem('reloaded')) {
        // The page was just reloaded. Clear the value from local storage
        // so that it will reload the next time this page is visited.
        localStorage.removeItem('reloaded');
    } else {
        // Set a flag so that we know not to reload the page twice.
        localStorage.setItem('reloaded', '1');
        location.reload();
    }
},
    methods: {
      showAddress(){
        
        if(this.selectedAddress!="")
        {
         
          this.$store.dispatch('setSchedule', this.selectedAddress)
          .then(response =>
          {
            this.raspored= response;

            if(this.raspored.length==0) 
            {
              if(this.selectedAddress!="") this.error=true;
              this.hide=true;
            }
            else{
              this.hide=false;
              this.error=false;
            }

            
          //  console.log(this.raspored);
          })
        
        }
       
        
      }
    },
    computed: {
    isComplete () {
      
      if(this.selectedAddress!="" || this.selectedAddress==null) return true
      return false
    }
  },
  name: 'Index'
}
</script>
<style scoped>
.box{
  background:rgba(0,0,0,0.6);
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
.table
{
  min-width:300px;
  margin:0 auto;
  background-color:rgba(0, 0, 0, 0.6);
  color:white;
  text-align: center;
  
}
.table th
{
  color:white;
  padding:10px;
}

.table tr
{
  padding:10px;
}

.center
{
  max-width:300px;
  margin:0 auto;
}
.red 
{
  padding:10px;
  background:rgba(0,0,0,0.6)
}

.red p
{
  color:red;
  font-size:1.5 rem;
  font-weight: bold;
}

</style>
