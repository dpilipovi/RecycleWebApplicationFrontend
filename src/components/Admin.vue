<template>
  <v-sheet
    height="92.4vh"
    class="overflow-hidden"
    style="position: relative;"
  >
  
    <v-btn
      @click.stop="drawer = !drawer"
    >
      <v-icon>mdi-menu</v-icon>
    </v-btn>


    <v-navigation-drawer
      v-model="drawer"
      absolute
      temporary
      color="teal"
    >
      <v-divider></v-divider>

      <v-list dense>

        <v-list-item
          v-for="item in items"
          :key="item.title"
          :to="item.link"
        >
          <v-list-item-icon>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
    <router-view></router-view>
  </v-sheet>
</template>
<script>
export default {
 data () {
      return {
        drawer: true,
        items: [
          { title: 'Users', icon: 'mdi-account', link: '/admin/users' },
          { title: 'Schedules', icon: 'mdi-calendar',  link: '/admin/schedules'  },
          { title: 'Vehicles', icon: 'mdi-truck',  link: '/admin/vehicles' }
        ],
      }
    },
    created()
    {
        let d = new Date()
        this.datum = d.getDate() +"."+(d.getMonth()+1)+"."+d.getFullYear()+"."
        console.log(this.datum)
    },
    name: 'Admin'
}
</script>
<style scoped>

</style>