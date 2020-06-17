<template>
  <v-card class="mx-auto" max-width="1300" shaped>
    <v-card-title class="font-weight-bold">Vehicles</v-card-title>
    <v-divider color="teal"></v-divider>
    <div class="py-3"></div>
    <v-btn @click="dialog = true">ADD VEHICLE</v-btn>
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
                  v-model="editedItem.brand"
                  :rules="nameRules"
                  label="Brand"
                  required
                ></v-text-field>

                <v-text-field
                  color="primary"
                  v-model="editedItem.firstRegistrationYear"
                  :rules="yearRules"
                  label="First registration year"
                  required
                  number
                ></v-text-field>
              </v-form>
            </v-row>
          </v-container>
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" text @click="close">Cancel</v-btn>
          <v-btn color="primary" :disabled="!valid" text @click="save"
            >save</v-btn
          >
        </v-card-actions>
      </v-card>
    </v-dialog>

    <v-dialog v-model="locationDialog" max-width="1300px">
      <template>
        <gmap-map
          :center="center"
          :zoom="zoom"
          style="width:100%;  height: 400px;"
        >
          <gmap-marker
            :key="index"
            v-for="(m, index) in markers"
            :position="m.position"
            @click="center = m.position"
          ></gmap-marker>
        </gmap-map>
      </template>
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
      v-if="vehicles != null"
      :headers="headers"
      :items="vehicles"
      :search="search"
      class="elevation-1"
    >
      <template v-slot:item.edit="{ item }">
        <v-icon @click="editItem(item)">mdi-pencil</v-icon>
      </template>
      <template v-slot:item.delete="{ item }">
        <v-icon @click="deleteItem(item)">mdi-trash-can</v-icon>
      </template>
      <template v-slot:item.location="{ item }">
        <v-icon @click="addMarker(item)">mdi-google-maps</v-icon>
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
  data() {
    return {
      dialog: false,
      search: "",
      vehicles: null,
      snackBar: false,
      snackBarText: "",
      snackBarColor: null,
      timeout: 2000,
      headers: [
        { text: "BRAND", value: "brand" },
        { text: "FIRST REGISTRATION YEAR", value: "firstRegistrationYear" },
        { text: "Edit", value: "edit", sortable: false },
        { text: "Delete", value: "delete", sortable: false },
        { text: "Location", value: "location", sortable: false },
      ],
      editedIndex: -1,
      editedItem: {
        id: null,
        brand: "",
        firstRegistrationYear: null,
        location: "",
      },
      defaultItem: {
        id: null,
        brand: "",
        firstRegistrationYear: null,
        location: "",
      },
      nameRules: [
        (v) => !!v || "Name is required",
        (v) => (v && v.length <= 20) || "Name must be less than 20 characters",
      ],
      yearRules: [
        (v) => !!v || "Required",
        (v) =>
          v <= new Date().getFullYear ||
          "First registration needs to be in past",
      ],
      valid: true,
      center: { lat: 45.813208, lng: 15.977374 },
      location: {lat: 0, lng: 0},
      markers: [],
      places: [],
      locationDialog: false,
      platform: {},
      map: {},
      apiKey: "AIzaSyAIFsjNZOTpPJpJ9rQei3Y8F2gAJ6OQoR4",
      latitude: null,
      longitude: null,
      zoom: 15,
      geocoder: null,
      currentPlace: null
    };
  },
  computed: {
    formTitle() {
      return this.editedIndex === -1 ? "New Vehicle" : "Edit Vehicle";
    },
  },
  mounted() {
    this.$store.dispatch("getVehicles").then((response) => {
      this.vehicles = response.data;
    });
  },

  methods: {
  
    addMarker(item) {

      this.$store.dispatch('getLongitudeAndLatitude', item.location)
        .then((response) => {
          this.location.lat =response.data.results[0].geometry.location.lat
          this.location.lng =response.data.results[0].geometry.location.lng
        })
        .catch((error) => {
          console.log(error);
        });

      /*if(!this.markers.includes(this.location))*/ this.markers.push({ position: this.location });
      /*if(!this.places.includes(item.location))*/ this.places.push(item.location);
      this.showLocation(item)
        
    },

    editItem(item) {
      this.editedIndex = this.vehicles.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.editedItem.location = this.vehicles[this.editedIndex].location;
      this.dialog = true;
    },

    deleteItem(item) {
      const index = this.vehicles.indexOf(item);
      confirm("Are you sure you want to delete this item?") &&
        this.vehicles.splice(index, 1);
        this.markers.splice(index, 1);
        this.places.splice(index, 1);

      this.$store.dispatch("deleteVehicle", item.id).then((response) => {
        if (response.status == 204) this.displaySnackbar("Vozilo izbrisano!");
        else this.displayErrorSnackbar("Dogodila se greška!");
      });
    },

    showDetails(item) {
      this.editedIndex = this.vehicles.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.detailDialog = true;
    },

    close() {
      this.dialog = false;
      this.locationDialog = false;
      this.dialogAdd = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      });
    },

    save() {
      if (this.editedIndex > -1) {
        Object.assign(this.vehicles[this.editedIndex], this.editedItem);
       // console.log(this.editedItem);
        this.$store
          .dispatch("editVehicle", this.editedItem)
          .then((response) => {
            if (response.status == 200) {
              this.displaySnackbar("Vozilo izmijenjeno!");
              this.editedIndex = -1;
            } else this.displayErrorSnackbar("Dogodila se greška!");
          });
      } else {
        this.$store.dispatch("addVehicle", this.editedItem).then((response) => {
          if (response.status == 201) {
            this.displaySnackbar("Vozilo dodano!");

            var vehicle = response.data;
            this.vehicles.push(vehicle);
          } else this.displayErrorSnackbar("Dogodila se greška!");
        });
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

    showLocation(item) {
      this.currentPlace = item.location;
      //console.log(this.location)
      var index = this.markers.findIndex(x => x.position === this.location)
      //console.log(index)
      this.center = this.markers[index].position;
      this.locationDialog = true;
    },
  
  }
};
</script>

<style></style>
