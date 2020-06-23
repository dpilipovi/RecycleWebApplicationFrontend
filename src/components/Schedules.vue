<template>
  <v-card class="mx-auto mb-12" max-width="80%" shaped>
    <v-card-title class="font-weight-bold">{{$t('admin.schedules_title')}}</v-card-title>
    <v-divider color="teal"></v-divider>
    <div class="py-3"></div>
    <v-btn @click="dialog = true">{{$t('admin.schedules_button_add')}}</v-btn>
    <v-dialog  v-model="dialog" @input="dialog => dialog || close()" max-width="500px">
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
                  :rules="addressRules"
                  :label="$t('admin.address')"
                  required
                ></v-text-field>

                <v-select :items="days" item-text="text" item-value="value" :label="$t('admin.day')" v-model="editedItem.day" required></v-select
                ><v-select :items="types" item-text="text" item-value="value" :label="$t('admin.type')" v-model="editedItem.type" required></v-select>
              </v-form>
            </v-row>
          </v-container>
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" text @click="close">{{$t('admin.button_cancel')}}</v-btn>
          <v-btn
            color="primary"
            :disabled="!valid"
            text
            @click="save"
            >{{$t('admin.button_save')}}</v-btn
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
          {{$t('admin.button_ok')}}
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
        { text: this.$t('admin.table_address'), value: "address" },
        { text: this.$t('admin.table_day'), value: "day" },
        { text: this.$t('admin.table_type'), value: "type" },
        { text: this.$t('admin.edit'), value: "edit", sortable: false },
        { text: this.$t('admin.delete'), value: "delete", sortable: false }
        
      ],
      days: [
            {text: this.$t('days.monday'), value: 1},
            {text: this.$t('days.tuesday'), value: 2},
            {text: this.$t('days.wednesday'), value: 3},
            {text: this.$t('days.thursday'), value: 4},
            {text: this.$t('days.friday'), value: 5}
      ],
      types: [
            {text: this.$t('types.plastic'), value: this.$t('types.plastic','en') },
            {text: this.$t('types.glass'), value: this.$t('types.glass','en') },
            {text: this.$t('types.metal'), value: this.$t('types.metal','en') },
            {text: this.$t('types.paper'), value: this.$t('types.paper','en') },
      ],
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
       addressRules: [
        (v) => !!v || this.$t('admin.required_address'),
        (v) => (v && v.length <= 250) || this.$t('admin.length_address'),
      ],
      valid: true,
      dialog: false
    }
  },
  computed: {
    formTitle() {
      return this.editedIndex === -1 ? this.$t('admin.new_schedule') : this.$t('admin.edit_schedule');
    },
  },
  mounted() {

    var that = this

    this.$store.dispatch("getSchedule").then((response) => {
      this.schedules = response.data;

       this.schedules.forEach(function(item) {
    
        that.format(item)

     });
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
      if(confirm(this.$t('admin.agree')))
      {
        this.schedules.splice(index, 1)

        this.$store.dispatch("deleteSchedule", item.id)
        .then((response) =>
            {
              if(response.status == 204) this.displaySnackbar(this.$t('admin.schedule_deleted'))
           
              else this.displayErrorSnackbar(this.$t('admin.error'))

            })
      }
    },

    showLocation(item) {
      this.editedIndex = this.schedules.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.locationDialog = true;
    },

    close() {
      this.dialog = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      });
    },

   save() {

      if (this.editedIndex > -1) {
        
        Object.assign(this.schedules[this.editedIndex], this.editedItem);
       
        this.$store.dispatch("editSchedule", this.editedItem)
        .then((response) =>
        {
         if(response.status == 200) 
         {
           var schedule = response.data

           this.format(schedule)

           console.log(schedule)

           this.schedules.splice(this.editedIndex, 1, schedule)

           console.log(this.schedules)
          
           this.displaySnackbar(this.$t('admin.schedule_edited'))
           this.editedIndex = -1
         }
             
          else this.displayErrorSnackbar(this.$t('admin.error'))
            
        
          });
      }
       else {
        this.$store.dispatch("addSchedule", this.editedItem)
        .then((response) => {
         if(response.status == 201)
        {
         this.displaySnackbar(this.$t('admin.schedule_added'))

         var schedule = response.data;
         this.schedules.push(schedule)


        }
        else this.displayErrorSnackbar(this.$t('admin.error'))
        
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
    },

    format(item)
    {
        if (item.day == 1) item.day = this.$t('days.monday')
        if (item.day == 2) item.day = this.$t('days.tuesday')
        if (item.day == 3) item.day = this.$t('days.wednesday')
        if (item.day == 4) item.day = this.$t('days.thursday')
        if (item.day == 5) item.day = this.$t('days.friday')

        if (item.type == "Glass") item.type = this.$t('types.glass')
        if (item.type == "Plastic") item.type = this.$t('types.plastic')
        if (item.type == "Metal") item.type = this.$t('types.metal')
        if (item.type == "Paper") item.type = this.$t('types.paper')
    }
  }
};
</script>

<style></style>
