<template>
<v-content>
 <v-container data-aos="fade-up" data-aos-duration="1000">
          <h2 class="display-2 font-weight-bold mb-3 text-uppercase text-center">{{$t('contact.title')}}</h2>

          <v-responsive
            class="mx-auto mb-12"
            width="56"
          >
            <v-divider class="mb-1"></v-divider>

            <v-divider></v-divider>
          </v-responsive>

          <v-theme-provider light>
            <v-row>
              <v-col cols="12">
                <v-text-field
                  flat
                  :label="$t('contact.name')"
                  solo
                  required
                ></v-text-field>
              </v-col>

              <v-col cols="12">
                <v-text-field
                  flat
                  :label="$t('contact.email')"
                  solo
                  required
                ></v-text-field>
              </v-col>

              <v-col cols="12">
                <v-text-field
                  flat
                  :label="$t('contact.subject')"
                  solo
                  required
                ></v-text-field>
              </v-col>

              <v-col cols="12">
                <v-textarea
                  flat
                  :label="$t('contact.message')"
                  solo
                  required
                ></v-textarea>
              </v-col>

              <v-col
                class="mx-auto"
                cols="auto"
              >
                <v-btn
                  color="accent"
                  x-large
                >
                  {{$t('contact.submit')}}
                </v-btn>
              </v-col>
            </v-row>
          </v-theme-provider>
        </v-container>

        <div class="py-6"></div>
    </v-content>
</template>
<style scoped>
</style>