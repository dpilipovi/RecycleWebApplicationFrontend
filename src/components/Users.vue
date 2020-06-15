<template>
   <v-card
    class="mx-auto"
    max-width="1300"
    shaped
  >
  
     <v-card-title class="font-weight-bold">USERS</v-card-title>
       <v-divider color="teal"></v-divider>
        <div class="py-3"></div>
        <v-dialog v-model="dialog" max-width="500px">
          <template v-slot:activator="{ on, attrs }">
            <v-col class="text-rigth" >
            <v-btn
              color="primary"
              dark
              v-bind="attrs"
              v-on="on"
              style="margin-right=20px"
            >New User</v-btn>
            </v-col>
          </template>
          <v-card>
            <v-card-title>
              <span class="headline">{{ formTitle }}</span>
            </v-card-title>

            <v-card-text>
              <v-container>
                <v-row>
                <v-form
                    align="center"
                    justify="end"
                    ref="form"
                    v-model="valid"
                    lazy-validation
                  >
                    <v-text-field
                      color="primary"
                      v-model="firstname"
                      :rules="nameRules"
                      label="Firstname"
                      required
                    ></v-text-field>

                    <v-text-field
                     color="primary"
                      v-model="lastname"
                      :rules="nameRules"
                      label="Lastname"
                      required
                    ></v-text-field>

                    <v-text-field
                      color="primary"
                      v-model="username"
                      :rules="nameRules"
                      label="Username"
                      required
                    ></v-text-field>

                    <v-text-field
                      color="primary"
                      v-model="email"
                      :rules="emailRules"
                      label="E-mail"
                      required
                    ></v-text-field>

                    <v-text-field
                      color="primary"
                      v-model="address"
                      :rules="nameRules"
                      label="Address"
                      required
                    ></v-text-field>

                    <v-text-field
                     color="primary"
                      :value="password"
                      label="Password"
                      :append-icon="value ? 'mdi-eye' : 'mdi-eye-off'"
                      @click:append="() => (value = !value)"
                      :type="value ? 'password' : 'text'"
                      :rules="passwordRules"
                      @input="(_) => (password = _)"
                    ></v-text-field>

                     <v-text-field
                      color="primary"
                      :value="password2"
                      label="Repeat password"
                      :append-icon="value2 ? 'mdi-eye' : 'mdi-eye-off'"
                      @click:append="() => (value2 = !value2)"
                      :type="value2 ? 'password' : 'text'"
                      :rules="password2Rules"
                      @input="(_) => (password2 = _)"
                    ></v-text-field>

                    <v-checkbox
                      color="primary"
                      v-model="checkbox"
                      label="Admin"
                    ></v-checkbox>
                </v-form>
                </v-row>
              </v-container>
            </v-card-text>

            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="primary" text @click="close">Cancel</v-btn>
              <v-btn color="primary" text @click="save">Save</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>

      <v-card-title>
      <v-text-field
        v-model="search"
        append-icon="search"
        label="Search"
        single-line
        hide-details
        color="teal"
      ></v-text-field>
    </v-card-title>


     <v-data-table v-if="users != null"
    :headers="headers"
    :items="users"
    :search="search"
    class="elevation-1"
  >
   <template v-slot:item.edit>
     <v-btn color="orange" dark><v-icon>mdi-pencil</v-icon></v-btn>
      </template>
   <template v-slot:item.delete>
        <v-btn color="red" dark><v-icon>mdi-trash-can</v-icon></v-btn>
      </template>
  
  </v-data-table>

  </v-card>
</template>

<script>
export default {
   data() {
    return {
      dialog: false,
      users: null,
      search: '',
      headers: [
          {
            text: 'FIRST NAME',
            align: 'start',
            value: 'firstname'
          
          },
          { text: 'LAST NAME', value: 'lastname' },
          { text: 'USERNAME', value: 'username' },
          { text: 'ADDRESS', value: 'address' },
          { text: 'EMAIL', value: 'email'},
          { text: 'ROLE', value: 'authorities'},
          { text: 'EDIT', value: 'edit'},
          { text: 'DELETE', value: 'delete' }
          
         
         ],

      editedIndex: -1,
      editedItem: {
        name: '',
        calories: 0,
        fat: 0,
        carbs: 0,
        protein: 0,
      },
      defaultItem: {
        name: '',
        calories: 0,
        fat: 0,
        carbs: 0,
        protein: 0,
      },
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
   computed: {
      formTitle () {
        return this.editedIndex === -1 ? 'New User' : 'Edit User'
      },
    },
    watch: {
      dialog (val) {
        val || this.close()
      },
    },
   mounted() {
    this.$store.dispatch("getUsers").then((response) => {
      this.users = response;
    });
   },
   methods:
   {
        editItem (item) {
        this.editedIndex = this.users.indexOf(item)
        this.editedItem = Object.assign({}, item)
        this.dialog = true
      },

      deleteItem (item) {
        const index = this.users.indexOf(item)
        confirm('Are you sure you want to delete this item?') && this.users.splice(index, 1)
      },

      close () {
        this.dialog = false
        this.$nextTick(() => {
          this.editedItem = Object.assign({}, this.defaultItem)
          this.editedIndex = -1
        })
      },

      save () {
        if (this.editedIndex > -1) {
          Object.assign(this.users[this.editedIndex], this.editedItem)
        } else {
          this.users.push(this.editedItem)
        }
        this.close()
      },
   }

}
</script>

<style>

</style>