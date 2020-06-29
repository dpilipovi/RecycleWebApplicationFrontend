<template>
  <v-content>
     <div class="text-center ma-2">
      <v-snackbar v-model="snackBar" :color="snackBarColor">
        {{ snackBarText }}
        <v-btn text @click="snackBar = false" :timeout="timeout">{{ $t("admin.button_ok") }}</v-btn>
      </v-snackbar>
    </div>
    <v-card class="mx-auto mb-12" max-width="80%" shaped data-aos="fade-left">
      <v-card-title class="font-weight-bold">
        {{
        $t("admin.recycles_title")
        }}
      </v-card-title>
      <v-divider color="teal"></v-divider>
      <div class="py-3"></div>
      <v-btn @click="addDialog = true">
        {{
        $t("admin.recycles_button_add")
        }}
      </v-btn>
      <v-dialog
        v-model="addDialog"
        @input="addDialog => addDialog || close()"
        max-width="500px"
        data-aos="fade-up"
      >
        <v-card>
          <v-card-title>
            <span class="headline">{{ formTitle }}</span>
          </v-card-title>
          <v-card-text>
            <v-container>
              <v-row>
                <v-form align="center" justify="end" ref="form" v-model="valid">
                  <v-select
                    :items="users"
                    item-text="username"
                    item-value="username"
                    :label="$t('admin.recycle_user')"
                    v-model="editedItem.username"
                    required
                  ></v-select>
                  <v-text-field
                    color="primary"
                    v-model="editedItem.amount"
                    :rules="amountRules"
                    :label="$t('admin.amount')"
                    type="number"
                    required
                  ></v-text-field>
                  <v-select
                    :items="types"
                    item-text="text"
                    item-value="value"
                    :label="$t('admin.type')"
                    v-model="editedItem.type"
                    required
                  ></v-select>
                  <v-menu
                    ref="menu"
                    v-model="menu"
                    :close-on-content-click="false"
                    transition="scale-transition"
                    offset-y
                    max-width="290px"
                    min-width="290px"
                    :return-value.sync="editedItem.yearMonth"
                  >
                    <template v-slot:activator="{ on, attrs }">
                      <v-text-field
                        label="Year and month"
                        prepend-icon="event"
                        readonly
                        v-bind="attrs"
                        v-on="on"
                        v-model="editedItem.yearMonth"
                      ></v-text-field>
                    </template>
                    <v-date-picker
                      v-model="editedItem.yearMonth"
                      type="month"
                      no-title
                      scrollable
                      :locale="locale"
                      min="2020-01"
                      :max="max"
                    >
                      <v-spacer></v-spacer>
                      <v-btn text color="primary" @click="menu = false">Cancel</v-btn>
                      <v-btn text color="primary" @click="$refs.menu.save(editedItem.yearMonth)">OK</v-btn>
                    </v-date-picker>
                  </v-menu>
                </v-form>
              </v-row>
            </v-container>
          </v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="primary" text @click="close">
              {{
              $t("admin.button_cancel")
              }}
            </v-btn>
            <v-btn color="primary" :disabled="!valid" text @click="save">
              {{
              $t("admin.button_save")
              }}
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
      <v-dialog
        v-model="dialog"
        @input="dialog => dialog || close()"
        max-width="500px"
        data-aos="fade-up"
      >
        <v-card>
          <v-card-title>
            <span class="headline">{{formTitle}}</span>
          </v-card-title>
          <v-card-text>
            <v-container>
              <v-row>
                <v-form align="center" justify="end" ref="form" v-model="valid">
                  <v-text-field
                    color="primary"
                    v-model="editedItem.amount"
                    :rules="amountRules"
                    :label="$t('admin.amount')"
                    type="number"
                    required
                  ></v-text-field>
                  <v-select
                    :items="types"
                    item-text="text"
                    item-value="value"
                    :label="$t('admin.type')"
                    v-model="editedItem.type"
                    required
                  ></v-select>
                  <v-menu
                    ref="menu"
                    v-model="menu"
                    :close-on-content-click="false"
                    transition="scale-transition"
                    offset-y
                    max-width="290px"
                    min-width="290px"
                    :return-value.sync="editedItem.yearMonth"
                  >
                    <template v-slot:activator="{ on, attrs }">
                      <v-text-field
                        label="Year and month"
                        prepend-icon="event"
                        readonly
                        v-bind="attrs"
                        v-on="on"
                        v-model="editedItem.yearMonth"
                      ></v-text-field>
                    </template>
                    <v-date-picker
                      v-model="editedItem.yearMonth"
                      type="month"
                      no-title
                      scrollable
                      :locale="locale"
                      min="2020-01"
                      :max="max"
                    >
                      <v-spacer></v-spacer>
                      <v-btn text color="primary" @click="menu = false">Cancel</v-btn>
                      <v-btn text color="primary" @click="$refs.menu.save(editedItem.yearMonth)">OK</v-btn>
                    </v-date-picker>
                  </v-menu>
                </v-form>
              </v-row>
            </v-container>
          </v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="primary" text @click="close">
              {{
              $t("admin.button_cancel")
              }}
            </v-btn>
            <v-btn color="primary" :disabled="!valid" text @click="save">
              {{
              $t("admin.button_save")
              }}
            </v-btn>
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
        v-if="recycles != null"
        :headers="headers"
        :items="recycles"
        :search="search"
        :sort-by="['username', 'id']"
        class="elevation-1"
        data-aos="fade-left"
      >
        <template v-slot:item.edit="{ item }">
          <v-icon @click="editItem(item)">mdi-pencil</v-icon>
        </template>
        <template v-slot:item.delete="{ item }">
          <v-icon @click="deleteItem(item)">mdi-trash-can</v-icon>
        </template>
      </v-data-table>
    </v-card>

  </v-content>
</template>

<script>
export default {
  data() {
    return {
      addDialog: false,
      username: "",
      recycleUser: null,
      max: null,
      locale: this.$i18n.locale,
      recycles: [],
      years: [],
      months: [],
      user_id: null,
      search: "",
      users: null,
      snackBar: false,
      snackBarText: "",
      snackBarColor: null,
      timeout: 2000,
      headers: [
        { text: this.$t("admin.table_id"), value: "id" },
        { text: this.$t("admin.table_user"), value: "username" },
        { text: this.$t("admin.table_type"), value: "type" },
        { text: this.$t("admin.table_amount"), value: "amount" },
        { text: this.$t("admin.table_year_month"), value: "yearMonth" },
        { text: this.$t("admin.edit"), value: "edit", sortable: false },
        { text: this.$t("admin.delete"), value: "delete", sortable: false }
      ],
      types: [
        {
          text: this.$t("types.plastic"),
          value: this.$t("types.plastic", "en")
        },
        { text: this.$t("types.glass"), value: this.$t("types.glass", "en") },
        { text: this.$t("types.metal"), value: this.$t("types.metal", "en") },
        { text: this.$t("types.paper"), value: this.$t("types.paper", "en") }
      ],
      editedIndex: -1,
      editedItem: {
        id: null,
        type: "",
        yearMonth: null,
        amount: null
      },
      defaultEditItem: {
        id: null,
        type: "",
        yearMonth: null,
        amount: null
      },
      addItem: {
        username: "",
        id: null,
        type: "",
        yearMonth: null,
        amount: null
      },
      defaultAddItem: {
        username: "",
        id: null,
        type: "",
        yearMonth: null,
        amount: null
      },
      amountRules: [
        v => !!v || this.$t("admin.required_amount"),
        v => (v && v > 0) || this.$t("admin.min_amount")
      ],
      valid: true,
      dialog: false,
      menu: false,
      modal: false,
      userIndex: null
    };
  },
  computed: {
    formTitle() {
      return this.editedIndex === -1
        ? this.$t("admin.new_recycle")
        : this.$t("admin.edit_recycle");
    }
  },
  mounted() {
    this.updateData();

    let currentTime = new Date();

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

    this.max =
      this.years[this.years.length - 1] +
      "-" +
      this.months[this.months.length - 1];
  },

  methods: {
    editItem(item) {
      console.log(item);
      this.userIndex = this.users.findIndex(x => x.username == item.username);
      console.log(this.userIndex);
      console.log(item);
      this.editedIndex = this.users[this.userIndex].recycles.findIndex(
        x => x.id == item.id
      );
      console.log(this.editedIndex);
      this.editedItem = Object.assign({}, item);
      this.dialog = true;
    },

    deleteItem(item) {
      const index = this.recycles.findIndex(x => x.id === item.id);
      const userIndex = this.users.findIndex(x => x.username === item.username);

      if (confirm(this.$t("admin.agree"))) {
        this.users[userIndex].recycles.splice(index, 1);
        this.recycles.splice(index, 1);

        var user = this.users[userIndex];
        user.password = "dont change";
        console.log(user);

        this.$store
          .dispatch("editUserRecycleData", user)
          .then(response => {
            if (response.status == 200)
              this.displaySnackbar(this.$t("admin.recycle_deleted"));
          })
          .catch(error => {
            console.log(error);
            this.displayErrorSnackbar(this.$t("admin.error"));
          });
      }
    },

    close() {
      this.dialog = false;
      this.addDialog = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      });
    },

    save() {
      console.log(this.editedIndex);
      if (this.editedIndex > -1) {
        //const username = this.editItem.user
        delete this.editedItem.username;

        this.editItem.yearMonth = parseInt(this.editItem.yearMonth);
        console.log(this.editedItem);
        Object.assign(
          this.users[this.userIndex].recycles[this.editedIndex],
          this.editedItem
        );

        this.users[this.userIndex].password = "dont change";

        console.log(this.users[this.userIndex]);

        this.$store
          .dispatch("editUserRecycleData", this.users[this.userIndex])
          .then(response => {
            if (response.status == 200) {
              var user = response.data;

              console.log(user);

              this.users.splice(this.editedIndex, 1, user);

              console.log(this.users);

              this.displaySnackbar(this.$t("admin.recycle_edited"));
              this.editedIndex = -1;
            }
          })
          .catch(error => {
            console.log(error);
            this.displayErrorSnackbar(this.$t("admin.error"));
          });
      } else {
        this.userIndex = this.users.findIndex(
          x => x.username == this.editedItem.username
        );
        delete this.editItem.username;
        this.users[this.userIndex].recycles.push(this.editedItem);
        this.users[this.userIndex].password = "dont change";

        this.$store
          .dispatch("editUserRecycleData", this.users[this.userIndex])
          .then(response => {
            if (response.status == 200) {
              this.displaySnackbar(this.$t("admin.recycle_added"));

              this.updateData();
            }
          })
          .catch(error => {
            console.log(error);
            this.displayErrorSnackbar(this.$t("admin.error"));
            this.users[this.userIndex].recycles.pop();
            delete this.users[this.userIndex].password;
          });
        this.addDialog = false;
      }
      this.close();
    },

    displaySnackbar(text) {
      this.snackBarText = text;
      this.snackBar = true;
      this.snackBarColor = "#333";
    },

    displayErrorSnackbar(text) {
      this.snackBarText = text;
      this.snackBar = true;
      this.snackBarColor = "red";
    },

    updateData() {
      this.recycles = [];
      var that = this;
      this.$store.dispatch("getUsers").then(response => {
        this.users = response.data;

        this.users.forEach(function(item) {
          item.recycles.forEach(function(item2) {
            var data = item2;
            data.username = item.username;

            that.recycles.push(data);
          });
        });
      });
    }
  }
};
</script>

<style></style>
