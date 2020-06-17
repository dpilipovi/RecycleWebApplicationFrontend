<template>
  <div id="app-idenx">
    <v-app id="index">
      <v-content class="grey lighten-3">
        <section id="home">
          <v-row no-gutters>
            <v-img height="94.9vh" src="../assets/images/background7.jpg">
              <div class="background-layer">
                <v-theme-provider dark>
                  <v-container
                    fill-height
                    data-aos="fade-up"
                    data-aos-duration="1000"
                  >
                    <v-row
                      align="center"
                      class="white--text mx-auto"
                      justify="center"
                    >
                      <v-col class="white--text text-center" cols="12" tag="h1">
                        <span
                          class="font-weight-light"
                          :class="[
                            $vuetify.breakpoint.smAndDown
                              ? 'display-1'
                              : 'display-2',
                          ]"
                          >{{$t('index.welcome1')}}</span
                        >

                        <br />

                        <span
                          :class="[
                            $vuetify.breakpoint.smAndDown
                              ? 'display-3'
                              : 'display-4',
                          ]"
                          class="font-weight-black"
                          >{{$t('index.welcome2')}}</span
                        >
                      </v-col>

                      <v-btn
                        class="align-self-end"
                        fab
                        outlined
                        @click="$vuetify.goTo('#about-me')"
                      >
                        <v-icon>mdi-chevron-double-down</v-icon>
                      </v-btn>
                    </v-row>
                  </v-container>
                </v-theme-provider>
              </div>
            </v-img>
          </v-row>
        </section>
        <section id="about-me">
          <div class="py-12"></div>
          <template>
            <v-container data-aos="fade-up" data-aos-duration="1000">
              <v-row align="center" justify="center">
                <span
                  :class="[
                    $vuetify.breakpoint.smAndDown ? 'display-2' : 'display-3',
                  ]"
                  class="font-weight-bold"
                  >{{$t('index.schedule')}}</span
                >
              </v-row>
              <div class="py-12 hidden-sm-and-down"></div>
              <div class="py-12 hidden-md-and-up"></div>

              <v-data-iterator
                :items="items"
                :items-per-page.sync="itemsPerPage"
                :page="page"
                :search="search"
                :sort-by="sortBy.toLowerCase()"
                :sort-desc="sortDesc"
                hide-default-footer
              >
                <template v-slot:header>
                  <v-toolbar dark color="blue darken-3" class="mb-1">
                    <v-text-field
                      v-model="search"
                      clearable
                      flat
                      solo-inverted
                      hide-details
                      :label="$t('index.search')"
                      prepend-inner-icon="search"
                    ></v-text-field>
                    <template v-if="$vuetify.breakpoint.mdAndUp">
                      <v-spacer></v-spacer>
                      <v-select
                        v-model="sortBy"
                        flat
                        solo-inverted
                        hide-details
                        :items="keys"
                        :label="$t('index.address')"
                        prepend-inner-icon="search"
                      ></v-select>
                      <v-spacer></v-spacer>
                      <v-btn-toggle v-model="sortDesc" mandatory>
                        <v-btn large depressed color="blue" :value="false">
                          <v-icon>mdi-arrow-up</v-icon>
                        </v-btn>
                        <v-btn large depressed color="blue" :value="true">
                          <v-icon>mdi-arrow-down</v-icon>
                        </v-btn>
                      </v-btn-toggle>
                    </template>
                  </v-toolbar>
                </template>

                <template v-slot:default="props">
                  <v-row>
                    <v-col
                      v-for="item in props.items"
                      :key="item.id"
                      cols="12"
                      sm="6"
                      md="4"
                      lg="3"
                    >
                      <v-card>
                        <v-card-title class="subheading font-weight-bold">{{
                          item.address
                        }}</v-card-title>

                        <v-divider></v-divider>

                        <v-list dense>
                          <v-list-item
                            v-for="(key, index) in filteredKeys"
                            :key="index"
                          >
                            <v-list-item-content
                              :class="{ 'blue--text': sortBy === key }"
                              >{{ $t('index.'+key.toLowerCase()) }}:</v-list-item-content
                            >
                            <v-list-item-content
                              class="align-end"
                              :class="{ 'blue--text': sortBy === key }"
                              >{{
                                item[key.toLowerCase()]
                              }}</v-list-item-content
                            >
                          </v-list-item>
                        </v-list>
                      </v-card>
                    </v-col>
                  </v-row>
                </template>

                <template v-slot:footer>
                  <v-row class="mt-2" align="center" justify="center">
                    <span class="grey--text">{{$t('index.items_per_page')}}</span>
                    <v-menu offset-y>
                      <template v-slot:activator="{ on }">
                        <v-btn dark text color="primary" class="ml-2" v-on="on">
                          {{ itemsPerPage }}
                          <v-icon>mdi-chevron-down</v-icon>
                        </v-btn>
                      </template>
                      <v-list>
                        <v-list-item
                          v-for="(number, index) in itemsPerPageArray"
                          :key="index"
                          @click="updateItemsPerPage(number)"
                        >
                          <v-list-item-title>{{ number }}</v-list-item-title>
                        </v-list-item>
                      </v-list>
                    </v-menu>

                    <v-spacer></v-spacer>

                    <span class="mr-4 grey--text"
                      >{{$t('index.page')}} {{ page }} {{$t('index.of')}} {{ numberOfPages }}</span
                    >
                    <v-btn
                      fab
                      dark
                      color="blue darken-3"
                      class="mr-1"
                      @click="formerPage"
                    >
                      <v-icon>mdi-chevron-left</v-icon>
                    </v-btn>
                    <v-btn
                      fab
                      dark
                      color="blue darken-3"
                      class="ml-1"
                      @click="nextPage"
                    >
                      <v-icon>mdi-chevron-right</v-icon>
                    </v-btn>
                  </v-row>
                </template>
              </v-data-iterator>
            </v-container>
          </template>
          <v-row align="center" class="white--text mx-auto" justify="center">
            <div class="py-12 hidden-sm-and-down"></div>
            <v-btn
              class="align-self-end"
              fab
              outlined
              @click="$vuetify.goTo('#contact')"
            >
              <v-icon>mdi-chevron-double-down</v-icon>
            </v-btn>
          </v-row>
          <div class="py-12 hidden-sm-and-down"></div>
          <div class="py-12 hidden-sm-and-down"></div>
          <div class="py-12 hidden-sm-and-down"></div>
        </section>

        <div class="py-12 hidden-sm-and-down"></div>
        <div class="py-5"></div>

        <Contact id="contact" />
        <v-row align="center" class="white--text mx-auto" justify="center">
          <v-btn
            class="align-self-end"
            fab
            outlined
            @click="$vuetify.goTo('#home')"
          >
            <v-icon>mdi-chevron-double-up</v-icon>
          </v-btn>
        </v-row>
        <div class="py-6"></div>
      </v-content>
    </v-app>
  </div>
</template>
<script>
import Contact from "@/components/Contact";

export default {
  data() {
    return {
      selectedAddress: "",
      hide: true,
      error: false,
      itemsPerPageArray: [4, 8, 12],
      search: "",
      filter: {},
      sortDesc: false,
      page: 1,
      itemsPerPage: 4,
      sortBy: "this.$t('index.address')",
      //keys: [this.$t('index.address'), this.$t('index.type'), this.$t('index.day')],
      keys: ["Address", "Type", "Day"],
      items: [],
    };
  },
  mounted() {
    this.$store.dispatch("getSchedule").then((response) => {
      this.items = response.data;

      let that = this
      
     this.items.forEach(function(item) {
    
        if (item.day == 1) item.day = that.$t('days.monday')
        if (item.day == 2) item.day = that.$t('days.tuesday')
        if (item.day == 3) item.day = that.$t('days.wednesday')
        if (item.day == 4) item.day = that.$t('days.thursday')
        if (item.day == 5) item.day = that.$t('days.friday')

        if (item.type == "Glass") item.type = that.$t('types.glass')
        if (item.type == "Plastic") item.type = that.$t('types.plastic')
        if (item.type == "Metal") item.type = that.$t('types.metal')
        if (item.type == "Paper") item.type = that.$t('types.paper')
     
     });
    });
  },
    
  computed: {
    numberOfPages() {
      return Math.ceil(this.items.length / this.itemsPerPage);
    },
    filteredKeys() {
      //this.keys = ["Address", "Type", "Day"]
    //  console.log(this.keys)
      return this.keys.filter((key) => key !== `Address`/*this.$t('index.address')*/);
    },
  },
  methods: {
    nextPage() {
      if (this.page + 1 <= this.numberOfPages) this.page += 1;
    },
    formerPage() {
      if (this.page - 1 >= 1) this.page -= 1;
    },
    updateItemsPerPage(number) {
      this.itemsPerPage = number;
    },
    // eslint-disable-next-line vue/no-dupe-keys
    filteredKeys(item) {
      //this.keys = ["Address", "Type", "Day"]
      console.log(this.keys)
      return this.keys.filter((key) => key !== item);
    },
  },
  components: {
    Contact,
  },
};
</script>
<style scoped>
.background-layer {
  background: rgba(0, 0, 0, 0.3);
  height: 94.9vh;
}
</style>
