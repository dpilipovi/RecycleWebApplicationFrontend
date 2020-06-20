<template>
  <v-card class="mx-auto" max-width="1300" shaped>
    <v-card-title class="font-weight-bold">{{$t('admin.users_title')}}</v-card-title>
    <v-divider color="teal"></v-divider>
    <div class="py-3"></div>
    <v-btn @click="dialogAdd=true"> {{$t('admin.users_button_add')}}</v-btn>
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
                  :rules="firstnameRules"
                  :label="$t('admin.firstname')"
                  required
                ></v-text-field>

                <v-text-field
                  color="primary"
                  v-model="editedItem.lastname"
                  :rules="lastnameRules"
                  :label="$t('admin.lastname')"
                  required
                ></v-text-field>

                <v-text-field
                  color="primary"
                  v-model="editedItem.username"
                  :rules="usernameRules"
                  :label="$t('admin.username')"
                  required
                ></v-text-field>

                <v-text-field
                  color="primary"
                  v-model="editedItem.email"
                  :rules="emailRules"
                  :label="$t('admin.email')"
                  required
                ></v-text-field>

                <v-text-field
                  color="primary"
                  v-model="editedItem.address"
                  :rules="addressRules"
                  :label="$t('admin.address')"
                  required
                ></v-text-field>

                <v-checkbox
                  color="primary"
                  v-model="changePassword"
                  :label="$t('admin.change_password')"
                ></v-checkbox>

                <v-text-field
                  v-show="changePassword"
                  color="primary"
                  :value="password"
                  :label="$t('admin.password')"
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
                  :label="$t('admin.password2')"
                  :append-icon="value2 ? 'mdi-eye' : 'mdi-eye-off'"
                  @click:append="() => (value2 = !value2)"
                  :type="value2 ? 'password' : 'text'"
                  :rules="password2Rules"
                  @input="(_) => (password2 = _)"
                ></v-text-field>

                <v-checkbox
                  color="primary"
                  v-model="admin"
                  :label="$t('admin.make_admin')"
                  v-show="!initialAdmin"
                ></v-checkbox>
              </v-form>
            </v-row>
          </v-container>
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" text @click="close">{{$t('admin.button_cancel')}}</v-btn>
          <v-btn
            color="primary"
            :disabled="!valid && changePassword == true"
            text
            @click="save"
            >{{$t('admin.button_save')}}</v-btn
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
                  :rules="firstnameRules"
                  :label="$t('admin.firstname')"
                  required
                ></v-text-field>

                <v-text-field
                  color="primary"
                  v-model="editedItem.lastname"
                  :rules="lastnameRules"
                  :label="$t('admin.lastname')"
                  required
                ></v-text-field>

                <v-text-field
                  color="primary"
                  v-model="editedItem.username"
                  :rules="usernameRules"
                  :label="$t('admin.username')"
                  required
                ></v-text-field>

                <v-text-field
                  color="primary"
                  v-model="editedItem.email"
                  :rules="emailRules"
                  :label="$t('admin.email')"
                  required
                ></v-text-field>

                <v-text-field
                  color="primary"
                  v-model="editedItem.address"
                  :rules="addressRules"
                  :label="$t('admin.address')"
                  required
                ></v-text-field>

                <v-text-field
                  color="primary"
                  :value="password"
                   :label="$t('admin.password')"
                  :append-icon="value ? 'mdi-eye' : 'mdi-eye-off'"
                  @click:append="() => (value = !value)"
                  :type="value ? 'password' : 'text'"
                  :rules="passwordRules"
                  @input="(_) => (password = _)"
                ></v-text-field>

                <v-text-field
                  color="primary"
                  :value="password2"
                   :label="$t('admin.password2')"
                  :append-icon="value2 ? 'mdi-eye' : 'mdi-eye-off'"
                  @click:append="() => (value2 = !value2)"
                  :type="value2 ? 'password' : 'text'"
                  :rules="password2Rules"
                  @input="(_) => (password2 = _)"
                ></v-text-field>

                <v-checkbox
                  color="primary"
                  v-model="admin"
                  :label="$t('admin.make_admin')"
                ></v-checkbox>
              </v-form>
            </v-row>
          </v-container>
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" text @click="close">{{$t('admin.button_cancel')}}</v-btn>
          <v-btn
            color="primary"
            :disabled="!valid && changePassword == true"
            text
            @click="save"
            >{{$t('admin.button_save')}}</v-btn
          >
        </v-card-actions>

      </v-card>
    </v-dialog>

    <v-dialog v-model="detailDialog" max-width="750px">
      <v-card>
      <v-card-title class="font-weight-bold tamno-siva">{{$t('admin.recycle_history')}}</v-card-title>

      <v-card-text>
      <v-container>  
      <v-row>
        <!--<v-col class="d-flex" cols="6"></v-col>-->
        <v-col class="d-flex" cols="2">
          <v-select
            :items="years"
            :label="$t('admin.year')"
            dense
            solo
            v-model="selectedYear"
            @change="yearChange()"
          ></v-select>
        </v-col>
        <v-col class="d-flex" cols="2">
          <v-select
            :items="months"
            :label="$t('admin.month')"
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
          <v-btn color="primary" text @click="close">{{$t('admin.button_close')}}</v-btn>
      </v-card-actions>
      </v-card>
    </v-dialog>

    <v-card-title>
      <v-text-field
        v-model="search"
        append-icon="search"
        :label="$t('admin.search')"
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
          {{$t('admin.button_ok')}}
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
        { text: this.$t('admin.users_firstname'), value: "firstname" },
        { text: this.$t('admin.users_lastname'), value: "lastname" },
        { text: this.$t('admin.users_username'), value: "username" },
        { text: this.$t('admin.users_address'), value: "address" },
        { text: this.$t('admin.users_email'), value: "email" },
        { text: this.$t('admin.users_role'), value: "authorities" },
        { text: this.$t('admin.edit'), value: "edit", sortable: false },
        { text: this.$t('admin.delete'), value: "delete", sortable: false },
        { text: this.$t('admin.details'), value: "details", sortable: false },
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
  computed: {
    formTitle() {
      return this.editedIndex === -1 ? this.$t('admin.new_user') : this.$t('admin.edit_user');
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
      if(confirm(this.$t('admin.agree')))
      { 
        this.users.splice(index, 1)

        this.$store.dispatch("deleteUser", item.username)
        .then((response) =>
            {
              if(response.status == 204) this.displaySnackbar(this.$t('admin.user_deleted'))
           
              else this.displayErrorSnackbar(this.$t('admin.error'))

            })

      }
    
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
        const index = this.editedIndex
        Object.assign(this.users[this.editedIndex], this.editedItem);
      
        this.$store.dispatch("editUserFromAdmin", this.editedItem)
        .then((response) =>
        {
         if(response.status == 200) this.displaySnackbar(this.$t('admin.user_edited'))
             
          else this.displayErrorSnackbar(this.$t('admin.error'))
          
          if (this.admin == true && this.initialAdmin==false && this.dialogAdd==false) {

            this.$store.dispatch("makeAdmin", response.data.username)
            .then((response) =>
            {
              if(response.status == 200) this.displaySnackbar(this.$t('admin.user_edited'))

              else this.displayErrorSnackbar(this.$t('admin.user_error'))
            
              this.users[index].authorities = new Array(
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
         this.displaySnackbar(this.$t('admin.user_added'))

         var user = response.data;
         this.users.push(user)
        }
        else this.displayErrorSnackbar(this.$t('admin.user_error'))
        
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
        { type: this.$t('types.plastic'), amount: 0 },
        { type: this.$t('types.paper'), amount: 0 },
        { type: this.$t('types.glass'), amount: 0 },
        { type: this.$t('types.metal'), amount: 0 },
      ];

      let yearMonth =
        this.selectedYear + "-" + this.to2digit(this.selectedMonth);


      let suma = 0;
      let maks = 0;
      let papir = 0;
      let plastika = 0;
      let staklo = 0;
      let metal = 0;

      this.editedItem.recycles.forEach(function(recycle) {
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
