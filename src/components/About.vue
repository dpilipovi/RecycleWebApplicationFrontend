<template>
  <v-content class="grey lighten-3">
    <section id="top">
      <v-row no-gutters>

   <video-background 
    :src="src"
    style="height: 94.2vh;"
 >
    <v-theme-provider dark>
              <v-container fill-height data-aos="fade-up" data-aos-duration="1000">
                <v-row align="center" class="white--text mx-auto" justify="center">
                  <v-col class="white--text text-center" cols="12" tag="h1">
                    <span
                      class="font-weight-bold"
                      :class="[
                            $vuetify.breakpoint.smAndDown
                              ? 'display-1'
                              : 'display-2',
                          ]"
                    >{{$t('about.title')}}</span>

                  </v-col>
                  <v-col class="text-center" cols="12">
                  <v-btn dark to="/register">{{$t('about.join_us')}} </v-btn>
                  </v-col>
                  <div class="py-12"></div>
                  <div class="py-12"></div>
                  <div class="py-12"></div>
                  <v-btn class="align-self-end" fab outlined @click="$vuetify.goTo('#mid')">
                    <v-icon >mdi-chevron-double-down</v-icon>
                  </v-btn>
                </v-row>
                

              </v-container>
    </v-theme-provider>
 </video-background>
      </v-row>
      <div class="py-6"></div>

    </section>
    <section id="mid" >
              <v-container fill-height data-aos="fade-up" data-aos-duration="1000">
                <v-row align="center" class=" mx-auto" justify="center">
                  <v-col class=" text-center" cols="12" tag="h1">
                    <span
                      class="font-weight-bold"
                      :class="[
                            $vuetify.breakpoint.smAndDown
                              ? 'display-1'
                              : 'display-2',
                          ]"
                    >{{$t('about.message')}}</span>
                      <div class="py-3"></div>

                <v-carousel>
                <v-carousel-item
                v-for="(item,i) in images"
                :key="i"
                :src="item.src"
                reverse-transition="fade-transition"
                transition="fade-transition"
                ></v-carousel-item>
            </v-carousel>
                  </v-col>
                  <v-btn class="align-self-end" fab outlined @click="$vuetify.goTo('#top')">
                    <v-icon >mdi-chevron-double-up</v-icon>
                  </v-btn>
                </v-row>
              </v-container>
    </section>
    <div class="py-12"></div>
    <div class="py-12"></div>
    <div class="py-6"></div>
  </v-content>
</template>
  
  <script>
export default 
{
     data () {
      return {
        images: [
          {
            src: require('@/assets/images/dirty1.jpg'),
          },
          {
            src: require('@/assets/images/dirty2.jpg'),
          },
          {
            src: require('@/assets/images/dirty3.jpg'),
          },
          {
            src:  require('@/assets/images/dirty4.jpg'),
          },
          {
            src: require('@/assets/images/recycle1.jpg'),
          },
          {
            src: require('@/assets/images/recycle2.jpg'),
          },
          {
            src: require('@/assets/images/recycle3.jpg'),
          },
          {
            src:  require('@/assets/images/recycle4.jpg'),
          },
          {
            src: require('@/assets/images/clean1.jpg'),
          },
          {
            src: require('@/assets/images/clean2.jpg'),
          },
          {
            src: require('@/assets/images/clean3.jpg'),
          },
          {
            src:  require('@/assets/images/clean4.jpg'),
          },
          
        ],
        
       src:  require('@/assets/images/video.mp4'),
      }
    },
}
</script>
  
  <style scoped>
  .background
  {
    background-color: rgba(0,0,0,0.5);
  }
</style>