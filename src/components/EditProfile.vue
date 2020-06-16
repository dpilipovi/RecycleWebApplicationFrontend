<template>
  <v-img height="93.2vh" src="../assets/images/profile_background4.jpg">
    <div class="background">
      <v-container>
        <v-col class="text-center" cols="12" tag="h1">
          <span
            class="font-weight-medium tamno-siva"
            :class="[
                            $vuetify.breakpoint.smAndDown
                              ? 'display-1'
                              : 'display-2',
                          ]"
          >Edit profile</span>
        </v-col>
        <v-layout align-center justify-center>
          <v-flex xs12 sm8 md4>
            <v-form align="center" justify="end" ref="form" v-model="valid" lazy-validation>
              <v-text-field
                color="#424242"
                v-model="user.firstname"
                :rules="nameRules"
                label="First name"
                required
                append-icon="person"
              ></v-text-field>

              <v-text-field
                color="#424242"
                v-model="user.lastname"
                :rules="nameRules"
                label="Last name"
                required
                append-icon="person"
              ></v-text-field>

              <v-text-field
                color="#424242"
                v-model="user.username"
                :rules="nameRules"
                label="Username"
                required
                append-icon="person"
              ></v-text-field>

              <v-text-field
                color="#424242"
                v-model="user.email"
                :rules="emailRules"
                label="E-mail"
                required
                append-icon="mail"
              ></v-text-field>

              <v-text-field
                color="#424242"
                v-model="user.address"
                :rules="nameRules"
                label="Address"
                required
                append-icon="home"
              ></v-text-field>

              <v-checkbox
                  color="#424242"
                  v-model="changePassword"
                  label="Change password"
              ></v-checkbox>

              <v-text-field
                v-show="changePassword"
                color="#424242"
                v-model="user.password"
                label="Password"
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
                label="Repeat password"
                :append-icon="value2 ? 'mdi-eye' : 'mdi-eye-off'"
                @click:append="() => (value2 = !value2)"
                :type="value2 ? 'password' : 'text'"
                :rules="password2Rules"
                @input="(_) => (password2 = _)"
                 required
              ></v-text-field>
            </v-form>
            <v-spacer></v-spacer>
            <v-btn :disabled="!valid && changePassword==true" color="success" @click="updateProfile()" dark>Edit</v-btn>
            <v-btn color="red" to="/profile" dark>Cancel</v-btn>
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
         v => !!v || "Password is required",
        (v) => (v && v.length >= 8) || "Password must be at least 8 characters",

              
      ],
      password2Rules: [
         v => (!!v && v) === this.password ||
              'Values do not match'
      ],
      nameRules: [
        (v) => !!v || "Name is required",
        (v) => (v && v.length <= 20) || "Name must be less than 20 characters",
      ],
      emailRules: [
        (v) => !!v || "E-mail is required",
        (v) => /.+@.+\..+/.test(v) || "E-mail must be valid",
      ]
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
