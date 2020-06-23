<template>
      <v-content>
         <v-img height="93.2vh" src="../assets/images/login_background.jpg">
        <v-container fluid fill-height>
          <v-layout align-center justify-center>
            <v-flex xs12 sm8 md4>
              <v-card class="mx-auto" max-width="500" color="white--text">
                <v-card-title class="teal" >{{$t('register.title')}}</v-card-title>
                <v-card-text>
                  <v-form
                    align="center"
                    justify="end"
                    ref="form"
                    v-model="valid"
                    lazy-validation
                  >
                    <v-text-field
                      color="teal"
                      v-model="firstname"
                      :rules="firstnameRules"
                      :label="$t('register.firstname')"
                      required
                    ></v-text-field>

                    <v-text-field
                      color="teal"
                      v-model="lastname"
                      :rules="lastnameRules"
                       :label="$t('register.lastname')"
                      required
                    ></v-text-field>

                    <v-text-field
                      color="teal"
                      v-model="username"
                      :rules="usernameRules"
                       :label="$t('register.username')"
                      required
                    ></v-text-field>

                    <v-text-field
                      color="teal"
                      v-model="email"
                      :rules="emailRules"
                      :label="$t('register.email')"
                      required
                    ></v-text-field>

                    <v-text-field
                      color="teal"
                      v-model="address"
                      :rules="addressRules"
                      :label="$t('register.address')"
                      required
                    ></v-text-field>

                    <v-text-field
                      color="teal"
                      :value="password"
                      :label="$t('register.password')"
                      :append-icon="value ? 'mdi-eye' : 'mdi-eye-off'"
                      @click:append="() => (value = !value)"
                      :type="value ? 'password' : 'text'"
                      :rules="passwordRules"
                      @input="(_) => (password = _)"
                    ></v-text-field>

                     <v-text-field
                      color="teal"
                      :value="password2"
                      :label="$t('register.password2')"
                      :append-icon="value2 ? 'mdi-eye' : 'mdi-eye-off'"
                      @click:append="() => (value2 = !value2)"
                      :type="value2 ? 'password' : 'text'"
                      :rules="password2Rules"
                      @input="(_) => (password2 = _)"
                    ></v-text-field>

                    <v-checkbox
                      color="teal"
                      v-model="checkbox"
                      :rules="[(v) => !!v ||  $t('register.agree1')]"
                      :label="$t('register.agree2')"
                      required
                    ></v-checkbox>

                    <v-btn
                      :disabled="!valid"
                      color="success"
                      class="mr-4"
                      @click="register"
                    >
                    {{$t('register.register_button')}}
                    </v-btn>

                    <!-- <v-btn color="warning" class="mr-4" @click="reset">
                      Reset Form
                    </v-btn> -->

                     <v-btn color="primary" to="/login"> 
                      {{$t('register.login_button')}}
                    </v-btn>
                  </v-form>
                </v-card-text>
              </v-card>
            </v-flex>
          </v-layout>
        </v-container>
         </v-img>
      </v-content>
</template>
<script>
import http from "../http-common";

export default {
  data() {
    return {
      username: "",
      password: "",
      email: "",
      address: "",
      password2: "",
      name: "",
      lastname: "",
      firstname: "",
      checkbox: false,
      valid: false,
      value: true,
      value2: true,
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
  methods: {
     validate () {
      this.$refs.form.validate()
    },
    reset () {
      this.$refs.form.reset()
    },
    resetValidation () {
      this.$refs.form.resetValidation()
    },
    register() {
      this.data = {
        username: this.username,
        password: this.password,
        firstname: this.firstname,
        lastname: this.lastname,
        email: this.email,
        address: this.address,
      };
      http
        .post("/user", this.data)
        .then((response) => {
          console.log(response.data);
          this.$router.push("/login");
        })
        .catch((e) => {
          console.log(e);
        });
    },
  },
  name: "Register",
};
</script>
<style scoped></style>
