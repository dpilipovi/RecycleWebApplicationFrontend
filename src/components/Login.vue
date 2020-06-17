<template>
    <v-content id="inspire">
      <v-img height="93.2vh" src="../assets/images/login_background.jpg">
      <v-container
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

          <v-content xs6 sm4 md2></v-content>
            <v-card class="elevation-12 ">
              <v-toolbar
                color="teal"
                flat
                class="text--white"
              >
                <v-toolbar-title class="white--text">{{$t('login.title')}}</v-toolbar-title>
                <v-spacer></v-spacer>
              </v-toolbar>
              <v-card-text>
                <v-form 
                    align="center"
                    justify="end"
                    ref="form"
                    v-model="valid"
                    lazy-validation>
                  <v-text-field
                    color="teal"
                    :label="$t('login.username')"
                    name="username"
                    prepend-icon="person"
                    type="text"
                    v-model="username"
                    :rules="nameRules"
                  ></v-text-field>

                  <v-text-field
                    color="teal"
                    id="password"
                    :label="$t('login.password')"
                    name="password"
                    prepend-icon="lock"
                    type="password"
                    v-model="password"
                    :rules="passwordRules"
                  ></v-text-field>
                </v-form>
              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="teal" :disabled="!valid" @click="login()" class="white--text">{{$t('login.button')}} </v-btn>
              </v-card-actions>
            </v-card>
          </v-flex>
        </v-layout>
      </v-container>
      </v-img>
    </v-content>
</template>
<script>
export default {
      data() {
      return {
       username: '',
       password: '',
       nameRules: [
        (v) => !!v || this.$t('login.username_required')
      ],
       passwordRules: [
        (v) => !!v || this.$t('login.password_required')
      ],
      valid: false,
      }
    },
   
    methods: {

    validate () {
      this.$refs.form.validate()
     },

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
        if(response.data.token)
        {
          this.$store.dispatch('getProfile').then(
           this.$router.push('/')
           //  setTimeout( () => this.$router.push({ path: '/'}), 1000);
          )
        }
        
      })
    
      }
    },
  name: 'Login'
}
</script>
<style scoped>

</style>
