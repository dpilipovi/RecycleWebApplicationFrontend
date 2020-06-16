<template>
  <v-card class="mx-auto" max-width="1300" shaped>
    <v-card-title class="font-weight-bold">SCHEDULES</v-card-title>
    <v-divider color="teal"></v-divider>
    <div class="py-3"></div>
    <v-btn @click="dialog = true">ADD SCHEDULE</v-btn>
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
                  v-model="editedItem.address"
                  :rules="nameRules"
                  label="Address"
                  required
                ></v-text-field>

                <v-select :items="days" label="Day" v-model="editedItem.day" required></v-select
                ><v-select :items="types" label="Type" v-model="editedItem.type" required></v-select>
              </v-form>
            </v-row>
          </v-container>
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" text @click="close">Cancel</v-btn>
          <v-btn
            color="primary"
            :disabled="!valid"
            text
            @click="save"
            >save</v-btn
          >
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
      v-if="schedules != null"
      :headers="headers"
      :items="schedules"
      :search="search"
      class="elevation-1"
    >
      <template v-slot:item.edit="{ item }">
        <v-icon @click="editItem(item)">mdi-pencil</v-icon>
      </template>
      <template v-slot:item.delete="{ item }">
        <v-icon  @click="deleteItem(item)"
          >mdi-trash-can</v-icon
        >
      </template>

    </v-data-table>

    <div class="text-center ma-2">
      <v-snackbar v-model="snackBar" :color="snackBarColor">
        {{ snackBarText }}
        <v-btn text @click="snackBar = false" :timeout="timeout">
          OK
        </v-btn>
      </v-snackbar>
    </div>
  </v-card>
</template>

<script>
export default {
  data()
  {
    return{
      search: '',
      schedules: null,
      snackBar: false,
      snackBarText: '',
      snackBarColor: null,
      timeout: 2000,
      headers: [
        { text: "ADDRESS", value: "address" },
        { text: "DAY", value: "day" },
        { text: "TYPE", value: "type" },
        { text: "EDIT", value: "edit", sortable: false },
        { text: "DELETE", value: "delete", sortable: false }
        
      ],
      days: ["Monday","Tuesday","Wednesday","Thursday","Friday"],
      types: ["Glass","Plastic","Paper","Metal"],
      editedIndex: -1,
      editedItem: {
        id: null,
        type: "",
        day: "",
        address: "",
      },
      defaultItem: {
        id: null,
        type: "",
        day: "",
        address: "",
      },
       nameRules: [
        (v) => !!v || "Address is required",
        (v) => (v && v.length <= 50) || "Address must be less than 50 characters",
      ],
      valid: true,
      dialog: false
    }
  },
  computed: {
    formTitle() {
      return this.editedIndex === -1 ? "New Schedule" : "Edit Schedule";
    },
  },
  mounted() {
    this.$store.dispatch("getSchedule").then((response) => {
      this.schedules = response.data;
    });
   },

  methods: {

   editItem(item) {
     
      this.editedIndex = this.schedules.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialog = true;
    },

     deleteItem(item) {
      const index = this.schedules.indexOf(item);
      confirm("Are you sure you want to delete this item?") &&
        this.schedules.splice(index, 1)

        this.$store.dispatch("deleteSchedule", item.id)
        .then((response) =>
            {
              if(response.status == 204) this.displaySnackbar("Raspored izbrisan!")
           
              else this.displayErrorSnackbar("Dogodila se greška!")

            })
    
    },

    showLocation(item) {
      this.editedIndex = this.schedules.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.locationDialog = true;
    },

    close() {
      this.dialog = false;
      this.locationlDialog = false;
      this.dialogAdd = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      });
    },

   save() {
      if (this.editedIndex > -1) {
     
        Object.assign(this.schedules[this.editedIndex], this.editedItem);
        console.log(this.editedItem)
        this.$store.dispatch("editSchedule", this.editedItem)
        .then((response) =>
        {
         if(response.status == 200) 
         {
           this.displaySnackbar("Raspored izmijenjenjen!")
           this.editedIndex = -1
         }
             
          else this.displayErrorSnackbar("Dogodila se greška!")
            
        
          });
      }
       else {
        this.$store.dispatch("addSchedule", this.editedItem)
        .then((response) => {
         if(response.status == 201)
        {
         this.displaySnackbar("Raspored dodan!")

         var schedule = response.data;
         this.schedules.push(schedule)
        }
        else this.displayErrorSnackbar("Dogodila se greška!")
        
    });
      
        
      }
      this.close();
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
  }
};
</script>

<style></style>
