<template>
  <v-img min-height="93.2vh" src="../assets/images/board.jpg">
    <v-row align="center" class="mx-auto" justify="center">
      <v-col class="text-center" cols="12" tag="h1"  data-aos="fade-up">
        <span
          :class="[$vuetify.breakpoint.smAndDown ? 'display-1' : 'display-2']"
          >{{ $t("announcements.title") }}</span
        >
      </v-col>
    </v-row>

    <!-- <div v-for="(announcement,index) in announcements" :key="index">
      <v-card  class="mx-auto mb-6" max-width="1300" shaped>
          <v-card-title> {{announcement.title}}</v-card-title>
            <v-divider color="teal"></v-divider>
          <v-card-text>
              {{announcement.message}}
          </v-card-text>
      </v-card>
  </div>-->
    <v-data-iterator
      :items="announcements"
      :items-per-page="itemsPerPage"
      width="80%"
      class="font-weight-black"
      sort-by="id"
      sort-desc="true"
    >
      <template v-slot:default="props">
        <v-col
          v-for="item in props.items"
          :key="item.name"
          cols="12"
          class="mx-auto"
        >
          <v-card class="mx-auto mb-6" width="80%" shaped data-aos="fade-up">
            <v-card-title> {{ item.title }}</v-card-title>
            <v-card-text>{{$t('announcements.created')}} {{ item.created | moment('timezone', 'Europe/Zagreb',"dddd, MMMM Do YYYY, h:mm:ss a") }}</v-card-text>
            <v-card-text v-if="item.updated != null">{{$t('announcements.updated')}} {{ item.updated | moment('timezone', 'Europe/Zagreb',"dddd, MMMM Do YYYY, h:mm:ss a") }}</v-card-text>
            <v-divider color="teal"></v-divider>
            <v-card-text>
              {{ item.message }}
            </v-card-text>
          </v-card>
        </v-col>
      </template>
    </v-data-iterator>
  </v-img>
</template>

<script>
export default {
  data() {
    return {
      announcements: [],
      itemsPerPage: 4,
      page: 1,
    };
  },
  mounted() {
    this.$store.dispatch("getAnnouncements").then((response) => {
      this.announcements = response.data;
      console.log(this.announcements)
    });
  },
};
</script>

<style></style>
