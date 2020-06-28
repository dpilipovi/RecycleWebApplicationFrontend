<template>
  <v-card class="mx-auto" max-width="1300" shaped>
    <v-card-title class="font-weight-bold">{{$t('admin.announcements_title')}}</v-card-title>
    <v-divider color="teal"></v-divider>
    <div class="py-3"></div>
    <v-btn @click="dialog = true">{{$t('admin.announcements_button_add')}}</v-btn>
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
                  v-model="editedItem.title"
                  :rules="titleRules"
                  :label="$t('admin.announcement_title')"
                  required
                ></v-text-field>
                  <v-text-field
                  color="primary"
                  v-model="editedItem.message"
                  :rules="messageRules"
                  :label="$t('admin.announcement_message')"
                  required
                ></v-text-field>

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

      <v-dialog v-model="detailDialog" max-width="750px">
          <v-card>
          <v-card-title>{{$t('admin.announcement_title')}}: {{editedItem.title}}</v-card-title>
           <v-divider></v-divider>
           <v-card-text>
           <p>{{$t('admin.announcement_message')}}: {{editedItem.message }}</p>
           <p>{{$t('admin.announcement_created')}}: {{editedItem.created | moment('timezone', 'Europe/Zagreb',"dddd, MMMM Do YYYY, h:mm:ss a")}}</p>
           <p>{{$t('admin.announcement_updated')}}: {{editedItem.updated | moment('timezone', 'Europe/Zagreb',"dddd, MMMM Do YYYY, h:mm:ss a")}}</p>
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
        label="Search"
        single-line
        hide-details
        color="teal"
      ></v-text-field>
    </v-card-title>

    <v-data-table
      v-if="announcements != null"
      :headers="headers"
      :items="announcements"
      :search="search"
      class="elevation-1"
    >
      <template v-slot:item.details="{ item }">
        <v-icon @click="showDetails(item)">mdi-eye</v-icon>
      </template>
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
      detailDialog: false,
      search: '',
      announcements: null,
      snackBar: false,
      snackBarText: '',
      snackBarColor: null,
      timeout: 2000,
      headers: [
        { text: this.$t('admin.announcement_id_table'), value: "id" },
        { text: this.$t('admin.announcement_title_table'), value: "title" },
        { text: this.$t('admin.details'), value: "details" },
        { text: this.$t('admin.edit'), value: "edit", sortable: false },
        { text: this.$t('admin.delete'), value: "delete", sortable: false }
        
      ],
      editedIndex: -1,
      editedItem: {
        id: null,
        title: "",
        message: "",
      },
      defaultItem: {
     id: null,
        title: "",
        message: "",
      },
       titleRules: [
        (v) => !!v || this.$t('admin.required_title'),
        (v) => (v && v.length <= 250) || this.$t('admin.length_title'),
      ],
      messageRules: [
        (v) => !!v || this.$t('admin.required_message'),
      ],
      valid: true,
      dialog: false
    }
  },
  computed: {
    formTitle() {
      return this.editedIndex === -1 ? this.$t('admin.new_announcement') : this.$t('admin.edit_announcement');
    },
  },
  mounted() {


    this.$store.dispatch("getAnnouncements").then((response) => {
      this.announcements = response.data;

     
    });

   },

  methods: {

   editItem(item) {
     
      this.editedIndex = this.announcements.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialog = true;
    },

     deleteItem(item) {
      const index = this.announcements.indexOf(item);
      if(confirm(this.$t('admin.agree')))
      {
        this.announcements.splice(index, 1)

        this.$store.dispatch("deleteAnnouncement", item.id)
        .then((response) =>
            {
              if(response.status == 204) this.displaySnackbar(this.$t('admin.announcement_deleted'))
           
              else this.displayErrorSnackbar(this.$t('admin.error'))

            })
      }
    },

  showDetails(item) {
      this.editedIndex = this.announcements.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.detailDialog = true;
    },

    close() {
      this.dialog = false;
      this.detailDialog = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      });
    },

   save() {

      if (this.editedIndex > -1) {
        
        Object.assign(this.announcements[this.editedIndex], this.editedItem);
       
        this.$store.dispatch("editAnnouncement", this.editedItem)
        .then((response) =>
        {
         if(response.status == 200) 
         {
           var announcement = response.data


           this.announcements.splice(this.editedIndex, 1, announcement)

         
          
           this.displaySnackbar(this.$t('admin.announcement_edited'))
           this.editedIndex = -1
         }
             
          else this.displayErrorSnackbar(this.$t('admin.error'))
            
        
          });
      }
       else {
        this.$store.dispatch("addAnnouncement", this.editedItem)
        .then((response) => {
         if(response.status == 201)
        {
         this.displaySnackbar(this.$t('admin.announcement_added'))

         var announcement = response.data;
         this.announcements.push(announcement)


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

  }
};
</script>

<style></style>
