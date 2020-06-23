<template>
  <v-img height="93.2vh" src="../assets/images/profile_background4.jpg">
    <div class="background">
      <v-container data-aos="fade-up">
        <v-col class="text-center" cols="12" tag="h1">
          <span
            class="font-weight-medium tamno-siva"
            :class="[
                            $vuetify.breakpoint.smAndDown
                              ? 'display-1'
                              : 'display-2',
                          ]"
          >{{$t('edit_profile.title')}}</span>
        </v-col>
        <v-layout align-center justify-center>
          <v-flex xs12 sm8 md4>
            <v-form align="center" justify="end" ref="form" v-model="valid" >
              <v-text-field
                color="#424242"
                v-model="user.firstname"
                :rules="firstnameRules"
                :label="$t('edit_profile.firstname')"
                required
                append-icon="person"
              ></v-text-field>

              <v-text-field
                color="#424242"
                v-model="user.lastname"
                :rules="lastnameRules"
                :label="$t('edit_profile.lastname')"
                required
                append-icon="person"
              ></v-text-field>

              <v-text-field
                color="#424242"
                v-model="user.username"
                :rules="usernameRules"
                :label="$t('edit_profile.username')"
                required
                append-icon="person"
              ></v-text-field>

              <v-text-field
                color="#424242"
                v-model="user.email"
                :rules="emailRules"
                :label="$t('edit_profile.email')"
                required
                append-icon="mail"
              ></v-text-field>

              <v-text-field
                color="#424242"
                v-model="user.address"
                :rules="addressRules"
                :label="$t('edit_profile.address')"
                required
                append-icon="home"
              ></v-text-field>

              <v-checkbox
                  color="#424242"
                  v-model="changePassword"
                  :label="$t('edit_profile.change_password')"
              ></v-checkbox>

              <v-text-field
                v-show="changePassword"
                color="#424242"
                v-model="user.password"
                :label="$t('edit_profile.password')"
                :append-icon="value ? 'mdi-eye' : 'mdi-eye-off'"
                @click:append="() => (value = !value)"
                :type="value ? 'password' : 'text'"
                :rules="passwordRules"
                @input="(_) => (password = _)"
                required
              ></v-text-field>

              <v-text-field
                v-show="changePassword"
                color="#424242"
                :value="pass2"
                :label="$t('edit_profile.password2')"
                :append-icon="value2 ? 'mdi-eye' : 'mdi-eye-off'"
                @click:append="() => (value2 = !value2)"
                :type="value2 ? 'password' : 'text'"
                :rules="password2Rules"
                @input="(_) => (password2 = _)"
                 required
              ></v-text-field>
            </v-form>
            <v-spacer></v-spacer>
            <v-btn :disabled="!valid && changePassword==true" color="success" @click="updateProfile()" dark>{{$t('edit_profile.edit_button')}}</v-btn>
            <v-btn color="red" to="/profile" dark>{{$t('edit_profile.cancel_button')}}</v-btn>
          </v-flex>
        </v-layout>
      </v-container>
    </div>
  </v-img>
</template>
<script>
export default {
  data() {
    return {
      profile: this.$store.state.profile,
      user: {
        id: null,
        password: "",
        firstname: "",
        lastname: "",
        username: "",
        email: "",
        address: "",
       // recycles: null,
       // authorities: null
      },
      pass2: "",
      valid: false,
      value: true,
      value2: true,
      changePassword: false,
      passwordRules: [
         v => !!v || this.$t('register.required_password'),
        (v) => (v && v.length >= 8) || this.$t('register.length_password')
      ],
      password2Rules: [
         v => (!!v && v) === this.password ||
              this.$t('register.not_match_password'),
      ],
      firstnameRules: [
        (v) => !!v || this.$t('register.required_firstname'),
        (v) => (v && v.length <= 20) || this.$t('register.length_firstname'),
      ],
      emailRules: [
        (v) => !!v || this.$t('register.required_email'),
        (v) => /.+@.+\..+/.test(v) || this.$t('register.valid_email'),
      ],
      addressRules: [
        (v) => !!v || this.$t('register.required_address'),
        (v) => (v && v.length <= 250) || this.$t('admin.length_address'),
      ],
      lastnameRules: [
        (v) => !!v || this.$t('register.required_lastname'),
        (v) => (v && v.length <= 50) || this.$t('register.length_lastname'),
      ],
       usernameRules: [
        (v) => !!v || this.$t('register.required_username'),
        (v) => (v && v.length <= 50) || this.$t('register.length_username'),
      ],
    };
  },
  created() {
    this.user.id = this.profile.id;
    this.user.firstname = this.profile.firstname;
    this.user.lastname = this.profile.lastname;
    this.user.username = this.profile.username;
    this.user.email = this.profile.email;
    this.user.address = this.profile.address;
    this.user.password = ''
   // this.user.authorities = this.profile.authorities
   // this.user.recycles = this.profile.user_recycle
  },

  methods: {
     validate () {
      this.$refs.form.validate()
    },
    updateProfile() {

      if(this.changePassword == false) this.user.password="dont change" 
   
      this.$store.dispatch("editUser", this.user).then(response => {
        console.log(response);
        /*if(this.user.password != "") */
        this.$router.push("/login");
      });
    }
  },
  name: "EditProfile"
};
</script>
<style scoped>
.button {
  color: white;
}
.input {
  background: rgba(0, 0, 0, 0.6);
  color: white;
}
::placeholder {
  color: gray;
}
.background {
  background-color: rgba(0, 0, 0, 0.2);
  height: 93.2vh;
}
.background2 {
  background-color: rgba(128, 128, 128, 0.05);
}
.tamno-siva {
  color: #424242;
}
</style>
