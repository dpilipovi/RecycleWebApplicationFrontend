<template>
      <v-content>
         <v-img height="93.2vh" src="../assets/images/login_background.jpg">
        <v-container fluid fill-height>
          <v-layout align-center justify-center>
            <v-flex xs12 sm8 md4>
              <v-card class="mx-auto" max-width="500" color="white--text">
                <v-card-title class="teal" >Register</v-card-title>
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
                      :rules="nameRules"
                      label="Firstname"
                      required
                    ></v-text-field>

                    <v-text-field
                      color="teal"
                      v-model="lastname"
                      :rules="nameRules"
                      label="Lastname"
                      required
                    ></v-text-field>

                    <v-text-field
                      color="teal"
                      v-model="username"
                      :rules="nameRules"
                      label="Username"
                      required
                    ></v-text-field>

                    <v-text-field
                      color="teal"
                      v-model="email"
                      :rules="emailRules"
                      label="E-mail"
                      required
                    ></v-text-field>

                    <v-text-field
                      color="teal"
                      v-model="address"
                      :rules="nameRules"
                      label="Address"
                      required
                    ></v-text-field>

                    <v-text-field
                      color="teal"
                      :value="password"
                      label="Password"
                      :append-icon="value ? 'mdi-eye' : 'mdi-eye-off'"
                      @click:append="() => (value = !value)"
                      :type="value ? 'password' : 'text'"
                      :rules="passwordRules"
                      @input="(_) => (password = _)"
                    ></v-text-field>

                     <v-text-field
                      color="teal"
                      :value="password2"
                      label="Repeat password"
                      :append-icon="value2 ? 'mdi-eye' : 'mdi-eye-off'"
                      @click:append="() => (value2 = !value2)"
                      :type="value2 ? 'password' : 'text'"
                      :rules="password2Rules"
                      @input="(_) => (password2 = _)"
                    ></v-text-field>

                    <v-checkbox
                      color="teal"
                      v-model="checkbox"
                      :rules="[(v) => !!v || 'You must agree to continue!']"
                      label="Do you agree?"
                      required
                    ></v-checkbox>

                    <v-btn
                      :disabled="!valid"
                      color="success"
                      class="mr-4"
                      @click="register"
                    >
                      Register!
                    </v-btn>

                    <!-- <v-btn color="warning" class="mr-4" @click="reset">
                      Reset Form
                    </v-btn> -->

                     <v-btn color="primary" to="/login"> 
                      Login
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
      valid: true,
      value: true,
      value2: true,
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
