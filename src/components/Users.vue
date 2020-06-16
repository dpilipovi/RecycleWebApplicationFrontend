<template>
  <v-card class="mx-auto" max-width="1300" shaped>
    <v-card-title class="font-weight-bold">USERS</v-card-title>
    <v-divider color="teal"></v-divider>
    <div class="py-3"></div>
    <v-btn @click="dialogAdd=true">ADD USER </v-btn>
    <v-dialog v-model="dialog" max-width="500px">
      <v-card>
        <v-card-title>
          <span class="headline">{{ formTitle }}</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-form align="center" justify="end" ref="form" v-model="valid">
                <v-text-field
                  color="primary"
                  v-model="editedItem.firstname"
                  :rules="nameRules"
                  label="Firstname"
                  required
                ></v-text-field>

                <v-text-field
                  color="primary"
                  v-model="editedItem.lastname"
                  :rules="nameRules"
                  label="Lastname"
                  required
                ></v-text-field>

                <v-text-field
                  color="primary"
                  v-model="editedItem.username"
                  :rules="nameRules"
                  label="Username"
                  required
                ></v-text-field>

                <v-text-field
                  color="primary"
                  v-model="editedItem.email"
                  :rules="emailRules"
                  label="E-mail"
                  required
                ></v-text-field>

                <v-text-field
                  color="primary"
                  v-model="editedItem.address"
                  :rules="nameRules"
                  label="Address"
                  required
                ></v-text-field>

                <v-checkbox
                  color="primary"
                  v-model="changePassword"
                  label="Change password"
                ></v-checkbox>

                <v-text-field
                  v-show="changePassword"
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
                  v-show="changePassword"
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
                  v-model="admin"
                  label="Make admin"
                  v-show="!initialAdmin"
                ></v-checkbox>
              </v-form>
            </v-row>
          </v-container>
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" text @click="close">Cancel</v-btn>
          <v-btn
            color="primary"
            :disabled="!valid && changePassword == true"
            text
            @click="save"
            >save</v-btn
          >
        </v-card-actions>
      </v-card>
    </v-dialog>

    <v-dialog v-model="dialogAdd" max-width="500px">
      <v-card>
        <v-card-title>
          <span class="headline">{{ formTitle }}</span>
        </v-card-title>

          <v-card-text>
          <v-container>
            <v-row>
              <v-form align="center" justify="end" ref="form" v-model="valid">
                <v-text-field
                  color="primary"
                  v-model="editedItem.firstname"
                  :rules="nameRules"
                  label="Firstname"
                  required
                ></v-text-field>

                <v-text-field
                  color="primary"
                  v-model="editedItem.lastname"
                  :rules="nameRules"
                  label="Lastname"
                  required
                ></v-text-field>

                <v-text-field
                  color="primary"
                  v-model="editedItem.username"
                  :rules="nameRules"
                  label="Username"
                  required
                ></v-text-field>

                <v-text-field
                  color="primary"
                  v-model="editedItem.email"
                  :rules="emailRules"
                  label="E-mail"
                  required
                ></v-text-field>

                <v-text-field
                  color="primary"
                  v-model="editedItem.address"
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
                  v-model="admin"
                  label="Make admin"
                ></v-checkbox>
              </v-form>
            </v-row>
          </v-container>
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" text @click="close">Cancel</v-btn>
          <v-btn
            color="primary"
            :disabled="!valid && changePassword == true"
            text
            @click="save"
            >Save</v-btn
          >
        </v-card-actions>

      </v-card>
    </v-dialog>

    <v-dialog v-model="detailDialog" max-width="750px">
      <v-card>
      <v-card-title class="font-weight-bold tamno-siva">Recycle history</v-card-title>

      <v-card-text>
      <v-container>  
      <v-row>
        <!--<v-col class="d-flex" cols="6"></v-col>-->
        <v-col class="d-flex" cols="2">
          <v-select
            :items="years"
            label="Year"
            dense
            solo
            v-model="selectedYear"
            @change="yearChange()"
          ></v-select>
        </v-col>
        <v-col class="d-flex" cols="2">
          <v-select
            :items="months"
            label="Month"
            dense
            solo
            v-model="selectedMonth"
            @change="getRecycleData()"
          ></v-select>
        </v-col>
      </v-row>

      <v-row>
        <v-col cols="6">
          <div v-for="(r, index) in recycleData" :key="index">
            <span class="font-weight-medium">{{ r.type }}</span>
            <v-progress-linear
              height="20px"
              rounded
              :value="r.amount * multiplier"
              color="primary"
              ><strong>{{ r.amount }}kg</strong></v-progress-linear
            >
            <br />
          </div>
        </v-col>
      </v-row>
      </v-container>
      </v-card-text>
      <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" text @click="close">Close</v-btn>
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

    <v-data-table
      v-if="users != null"
      :headers="headers"
      :items="users"
      :search="search"
      class="elevation-1"
    >
      <template v-slot:item.edit="{ item }">
        <v-icon @click="editItem(item)">mdi-pencil</v-icon>
      </template>
      <template v-slot:item.delete="{ item }">
        <v-icon v-show="!isAdmin(item)" @click="deleteItem(item)"
          >mdi-trash-can</v-icon
        >
      </template>
      <template v-slot:item.details="{ item }">
        <v-icon @click="showDetails(item)">mdi-eye</v-icon>
      </template>
    </v-data-table>

        <div class="text-center ma-2">
    <v-snackbar
      v-model="snackBar"
      :color="snackBarColor"
    >
      {{ snackBarText }}
        <v-btn
          text
          @click="snackBar = false"
          :timeout="timeout"
          
        >
          OK
        </v-btn>
    </v-snackbar>
  </div>
  </v-card>
</template>

<script>
export default {
  data() {
    return {
      admin: null,
      initialAdmin: null,
      detailDialog: false,
      dialog: false,
      dialogAdd: false,
      users: null,
      changePassword: false,
      search: "",
      recycleData: [],
      sum: 0,
      max: 0,
      years: [],
      months: [],
      currentYear: 0,
      currentMonth: 0,
      selectedMonth: new Date().getMonth() + 1,
      selectedYear: new Date().getFullYear(),
      multiplier: 0,
      snackBar: false,
      snackBarText: '',
      snackBarColor: null,
      timeout: 2000,
      headers: [
        { text: "FIRST NAME", value: "firstname" },
        { text: "LAST NAME", value: "lastname" },
        { text: "USERNAME", value: "username" },
        { text: "ADDRESS", value: "address" },
        { text: "EMAIL", value: "email" },
        { text: "ROLE", value: "authorities" },
        { text: "EDIT", value: "edit", sortable: false },
        { text: "DELETE", value: "delete", sortable: false },
        { text: "DETAILS", value: "details", sortable: false },
      ],

      editedIndex: -1,
      editedItem: {
        id: null,
        firstname: "",
        lastname: "",
        username: "",
        address: "",
        email: "",
        password: "",
        recycles: null,
        authorities: null
      },
      defaultItem: {
        id: null,
        firstname: "",
        lastname: "",
        username: "",
        address: "",
        email: "",
        password: "",
        recycles: null,
        authorities: null
      },
      password: "",
      password2: "",
      valid: true,
      value: true,
      value2: true,
      passwordRules: [
        (v) => !!v || "Password is required",
        (v) => (v && v.length >= 8) || "Password must be at least 8 characters",
      ],
      password2Rules: [
        (v) => (!!v && v) === this.password || "Values do not match",
      ],
      nameRules: [
        (v) => !!v || "Name is required",
        (v) => (v && v.length <= 20) || "Name must be less than 20 characters",
      ],
      emailRules: [
        (v) => !!v || "E-mail is required",
        (v) => /.+@.+\..+/.test(v) || "E-mail must be valid",
      ],
    };
  },
  computed: {
    formTitle() {
      return this.editedIndex === -1 ? "New User" : "Edit User";
    },
  },
  created() {
    let currentTime = new Date();

    this.currentYear = currentTime.getFullYear();
    this.currentMonth = currentTime.getMonth() + 1;

    let i = currentTime.getFullYear();
    while (i >= 2020) {
      this.years.push(i);
      i--;
    }
    this.years.reverse();

    i = currentTime.getMonth() + 1;
    while (i > 0) {
      this.months.push(i);
      i--;
    }

    this.months.reverse();
  },
  watch: {
    dialog(val) {
      val || this.close();
    },
  },
  mounted() {
    this.$store.dispatch("getUsers").then((response) => {
      this.users = response.data;
    });
  },
  methods: {
    editItem(item) {
      if(this.dialogAdd == false)
      {
      if (this.isAdmin(item)) {
        this.admin = true;
        this.initialAdmin = true;
      } else {
        this.admin = false;
        this.initialAdmin = false;
      }
      }

      this.editedIndex = this.users.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialog = true;
    },

    deleteItem(item) {
      const index = this.users.indexOf(item);
      confirm("Are you sure you want to delete this item?") &&
        this.users.splice(index, 1)

        this.$store.dispatch("deleteUser", item.username)
        .then((response) =>
            {
              if(response.status == 204) this.displaySnackbar("Korisnik izbrisan!")
           
              else this.displayErrorSnackbar("Dogodila se greška!")

            })
    
    },

    showDetails(item) {
      this.editedIndex = this.users.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.detailDialog = true;
    },

    close() {
      this.dialog = false;
      this.detailDialog = false;
      this.dialogAdd = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      });
    },

    save() {
      if (this.editedIndex > -1) {
        if (this.changePassword == true)
          this.editedItem.password = this.password;
        else this.editedItem.password = "dont change";

        Object.assign(this.users[this.editedIndex], this.editedItem);
        this.$store.dispatch("editUserFromAdmin", this.editedItem)
        .then((response) =>
        {
         if(response.status == 200) this.displaySnackbar("Korisnik izmijenjen!")
             
          else this.displayErrorSnackbar("Dogodila se greška!")
            
          if (this.admin == true && this.dialogAdd==false) {
            this.$store.dispatch("makeAdmin", this.editedItem.username)
            .then((response) =>
            {
              if(response.status == 200) this.displaySnackbar("Korisnik izmijenjen!")

              else this.displayErrorSnackbar("Dogodila se greška!")
            
              this.users[this.editedIndex].authorities = new Array(
                "ROLE_ADMIN"
              );
              this.admin = false;
              this.password = ''
              this.password2 = ''
            })
          }
        });
      } else {
        this.editedItem.password = this.password
        this.$store.dispatch("addUser", this.editedItem)
        .then((response) => {
         if(response.status == 201)
        {
         this.displaySnackbar("Korisnik dodan!")

         var user = response.data;
         this.users.push(user)
        }
        else this.displayErrorSnackbar("Dogodila se greška!")
        
    });
        
             
          
        
      }
      this.close();
    },

    isAdmin(item) {
      return item.authorities.includes("ROLE_ADMIN") ? true : false;
    },

    getRecycleData() {
      this.defaultPage = false;

      this.recycleData = [
        { type: "Plastic", amount: 0 },
        { type: "Paper", amount: 0 },
        { type: "Glass", amount: 0 },
        { type: "Metal", amount: 0 },
      ];

      let yearMonth =
        this.selectedYear + "-" + this.to2digit(this.selectedMonth);


      let suma = 0;
      let maks = 0;
      let papir = 0;
      let plastika = 0;
      let staklo = 0;
      let metal = 0;

      this.editedItem.user_recycle.forEach(function(recycle) {
        if (yearMonth == recycle.yearMonth) {


          if (recycle.type.toLowerCase() === "paper") papir += recycle.amount; //this.recycleData[0].amount+=data.amount;
          if (recycle.type.toLowerCase() == "plastic")
            plastika += recycle.amount; //this.recycleData[1].amount+=data.amount;
          if (recycle.type.toLowerCase() == "glass") staklo += recycle.amount; //this.recycleData[2].amount+=data.amount;
          if (recycle.type.toLowerCase() == "metal") metal += recycle.amount; //this.recycleData[3].amount+=data.amount;

          suma += recycle.amount;
          if (recycle.amount > maks) maks = recycle.amount;
        }
      });

      this.recycleData[0].amount = plastika;
      this.recycleData[1].amount = papir;
      this.recycleData[2].amount = staklo;
      this.recycleData[3].amount = metal;

      this.max = maks;
      this.multiplier = 100 / this.max;
      this.sum = suma;
    },

    yearChange() {
      if (this.selectedYear < new Date().getFullYear())
        for (let i = 1; i < 13; i++) this.months.push(i);
      else {
        let i = new Date().getMonth() + 1;

        while (i > 0) {
          this.months.push(i);
          i--;
        }

        this.months.reverse();
      }

      this.getRecycleData();
    },

    to2digit(month) {
      return ("00" + month).slice(-2);
    },

    displaySnackbar(text)
    {
      this.snackBarText = text
      this.snackBar = true
       this.snackBarColor = "#333"
    },

    displayErrorSnackbar(text)
    {
      this.snackBarText = text
      this.snackBar = true
      this.snackBarColor = "red"
    }

  },
};
</script>

<style></style>
