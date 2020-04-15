<template>
  <div id="app-register">
    <v-app id="register">
      <v-content>
        <v-container fluid fill-height>
          <v-layout align-center justify-center>
            <v-flex xs12 sm8 md4>
              <v-card class="mx-auto" max-width="500">
                <v-card-title>Register</v-card-title>
                <v-card-text>
                  <v-form
                    align="center"
                    justify="end"
                    ref="form"
                    v-model="valid"
                    lazy-validation
                  >
                    <v-text-field
                      v-model="firstname"
                      :rules="nameRules"
                      label="Firstname"
                      required
                    ></v-text-field>

                    <v-text-field
                      v-model="lastname"
                      :rules="nameRules"
                      label="Lastname"
                      required
                    ></v-text-field>

                    <v-text-field
                      v-model="username"
                      :rules="nameRules"
                      label="Username"
                      required
                    ></v-text-field>

                    <v-text-field
                      v-model="email"
                      :rules="emailRules"
                      label="E-mail"
                      required
                    ></v-text-field>

                    <v-text-field
                      v-model="address"
                      :rules="nameRules"
                      label="Address"
                      required
                    ></v-text-field>

                    <v-text-field
                      :value="password"
                      label="Password"
                      :append-icon="value ? 'mdi-eye' : 'mdi-eye-off'"
                      @click:append="() => (value = !value)"
                      :type="value ? 'password' : 'text'"
                      :rules="[rules.password]"
                      @input="(_) => (password = _)"
                    ></v-text-field>

                    <v-checkbox
                      v-model="checkbox"
                      :rules="[(v) => !!v || 'You must agree to continue!']"
                      label="Do you agree?"
                      required
                    ></v-checkbox>

                    <v-btn
                      :disabled="!valid"
                      color="success"
                      class="mr-4"
                      @click="validate"
                    >
                      Validate
                    </v-btn>

                    <v-btn color="error" class="mr-4" @click="reset">
                      Reset Form
                    </v-btn>

                    <v-btn color="warning" @click="resetValidation">
                      Reset Validation
                    </v-btn>
                  </v-form>
                </v-card-text>
              </v-card>
            </v-flex>
          </v-layout>
        </v-container>
      </v-content>
    </v-app>
  </div>
  <!-- <div class="hero-body">
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
    </div> -->
</template>
<script>
import http from "../http-common";

export default {
  data() {
    return {
      username: "",
      pass: "",
      email: "",
      address: "",
      pass2: "",
      name: "",
      lastname: "",
      valid: true,
      value: true,
      nameRules: [
        (v) => !!v || "Name is required",
        (v) => (v && v.length <= 10) || "Name must be less than 10 characters",
      ],
      emailRules: [
        (v) => !!v || "E-mail is required",
        (v) => /.+@.+\..+/.test(v) || "E-mail must be valid",
      ],
      rules: {
        required: (value) => !!value || "Required.",
        password: (value) => {
          const pattern =
            "/^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])(?=.{8,})/";
          return (
            pattern.test(value) ||
            "Min. 8 characters with at least one capital letter, a number and a special character."
          );
        },
      },
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
        password: this.pass,
        firstname: this.name,
        lastname: this.lastname,
        email: this.email,
        address: this.address,
      };
      http
        .post("/register", this.data)
        .then((response) => {
          console.log(response.data);
          if (response.data == true) this.$router.push("/login");
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
