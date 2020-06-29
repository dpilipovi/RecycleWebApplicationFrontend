<template>
  <v-img height="93.2vh" src="../assets/images/profile_background4.jpg">
    <div class="background">
      <v-container data-aos="fade-up">
        <v-row>
          <v-col cols="4"></v-col>
          <v-col class="text-center" cols="4" tag="h1">
            <span
              class="font-weight-medium tamno-siva"
              :class="[
                            $vuetify.breakpoint.smAndDown
                              ? 'display-1'
                              : 'display-2',
                          ]"
            >{{profile.firstname}} {{profile.lastname}}</span>
          </v-col>
          <v-col cols="4" align="end">
            <v-btn to="/editProfile">{{$t('profile.edit_button')}}</v-btn>
          </v-col>
        </v-row>
        <v-row>
          <!-- <v-col class="d-flex" cols="6"></v-col>-->
          <v-col class="d-flex" cols="6" tag="h1">
            <p class="font-weight-bold tamno-siva">{{$t('profile.history')}}</p>
          </v-col>
        </v-row>
        <v-row>
          <!--<v-col class="d-flex" cols="6"></v-col>-->
          <v-col class="d-flex" cols="2">
            <v-select
              :items="years"
              label="Year"
              dense
              solo
              v-model="selectedYear"
              @change="yearChange()"
            ></v-select>
          </v-col>
          <v-col class="d-flex" cols="2">
            <v-select
              :items="months"
              label="Month"
              dense
              solo
              v-model="selectedMonth"
              @change="getRecycleData()"
            ></v-select>
          </v-col>
          <v-col cols="2"></v-col>
           <v-col class="d-flex" cols="6" tag="h1">
            <v-btn @click="showGraph = !showGraph">{{ $t('profile.toggle_graph_button') }}</v-btn>
          </v-col>
        </v-row>

        <v-row>
          <v-col cols="6">
            <div v-for="(r,index) in recycleData" :key="index" data-aos="fade-right">
              <!--<p><progress class="progress is-danger" :data-label="r.amount +'kg'" :value="r.amount" :max="max" ></progress>{{r.type.name}}</p>
              <br>-->
              <span class="font-weight-medium">{{r.type}}</span>
              <v-progress-linear
                height="20px"
                rounded
                :value="r.amount*multiplier"
                color="grey darken-2"
              >
                <strong>{{r.amount}}kg</strong>
              </v-progress-linear>
              <br />
            </div>
          </v-col>
          <v-col cols="6">
           <apexchart  data-aos="fade-left" type="line" height="300px" :options="chartOptions" :series="series" v-show="showGraph"></apexchart>
          </v-col>
        </v-row>
      </v-container>
    </div>
  </v-img>
</template>
<script>

export default {
     data() {
      return {
        showGraph: false,
        allData: this.$store.state.profile.recycles,
        recycleData: [],
        sum: 0,
        max: 0,
        years : [],
        months : [],
        currentYear : 0,
        currentMonth : 0,
        selectedMonth :  new Date().getMonth()+1,
        selectedYear :  new Date().getFullYear(),
        profile : this.$store.state.profile,
        defaultPage : true,
        achievementData: [],
        multiplier: 0,
      
          series: [
            {
              name: this.$t('types.paper'),
              data: [0,0,0,0,0,0,0,0,0,0,0,0]
            },
            {
              name: this.$t('types.plastic'),
              data: [0,0,0,0,0,0,0,0,0,0,0,0]
            },
            {
              name: this.$t('types.glass'),
              data: [0,0,0,0,0,0,0,0,0,0,0,0]
            },
            {
              name: this.$t('types.metal'),
              data: [0,0,0,0,0,0,0,0,0,0,0,0]
            }
          ],
          chartOptions: {
            chart: {
              height: 300,
              type: 'line',
              dropShadow: {
                enabled: false,
                color: '#000',
                top: 18,
                left: 7,
                blur: 10,
                opacity: 0.2
              },
              toolbar: {
                show: false
              }
            },
            colors: ['#000000', '#0000FF', '#FF0000', '#00FF00'],
            dataLabels: {
              enabled: true,
            },
            stroke: {
              curve: 'smooth'
            },
            /*title: {
              text: 'naslov',
              align: 'center'
            },*/
            grid: {
              borderColor: '#e7e7e7',
              row: {
                colors: ['#777777'], // takes an array which will be repeated on columns
                opacity: 0.5
              },
            },
            markers: {
              size: 1
            },
            xaxis: {
              categories: [this.$t('months.jan'), this.$t('months.feb'), this.$t('months.mar'), this.$t('months.apr'), this.$t('months.may'), this.$t('months.jun'), this.$t('months.jul'),this.$t('months.aug'),this.$t('months.sep'),this.$t('months.oct'),this.$t('months.nov'),this.$t('months.dec')],
              title: {
                text: this.$t('months.months')
              }
            },
            yaxis: {
              title: {
                text: 'KG'
              },
              min: 0,
              max: 10
            },
            legend: {
              position: 'top',
              horizontalAlign: 'left',
              floating: true,
              offsetY: -25,
              offsetX: -5,
            }
          },
      }
    },
    created()
    {
       // console.log(this.allData)
       // let suma=0;
      //  let maks=0;

        let currentTime = new Date();

        this.currentYear=currentTime.getFullYear();
        this.currentMonth=currentTime.getMonth()+1;


            let i=currentTime.getFullYear()
            while(i>=2020)
            {
                this.years.push(i)
                i--

             }
             this.years.reverse()

             this.years.push(this.$t('profile.all'))


            i = currentTime.getMonth()+1
            while(i>0)
            {
                this.months.push(i)
                i--
            }

            this.months.reverse();

            this.months.push(this.$t('profile.all'))


            var that = this 

            this.recycleData = [{type: this.$t('types.plastic'), amount:0},{type : this.$t('types.paper'), amount:0},{type: this.$t('types.glass'), amount:0},{type: this.$t('types.metal'), amount:0}]
            let suma=0;
            let maks=0;
            let papir=0;
            let plastika=0;
            let staklo=0;
            let metal=0;

             i = currentTime.getMonth()+1

             this.allData.forEach(function(recycle)
            {
             
                if(i == recycle.yearMonth.slice(-2))
                {

                if(recycle.type.toLowerCase()==="paper") papir+=recycle.amount;
                if(recycle.type.toLowerCase()=="plastic") plastika+=recycle.amount;
                if(recycle.type.toLowerCase()=="glass")  staklo+=recycle.amount;
                if(recycle.type.toLowerCase()=="metal")  metal+=recycle.amount;

                suma+=recycle.amount
                if(recycle.amount>maks) maks=recycle.amount;
                }
            })

            this.recycleData[0].amount=plastika;
            this.recycleData[1].amount=papir;
            this.recycleData[2].amount=staklo;
            this.recycleData[3].amount=metal;

            this.max=maks;
            this.multiplier = 100/this.max;
            this.sum=suma;

            this.allData.forEach(function(recycle)
            {

              if(recycle.yearMonth.slice(-2) == "01") 
              {
                if(recycle.type.toLowerCase() === that.$t('types.paper','en').toLowerCase()) that.series[0].data[0]+=recycle.amount;
                if(recycle.type.toLowerCase() === that.$t('types.plastic','en').toLowerCase()) that.series[1].data[0]+=recycle.amount;
                if(recycle.type.toLowerCase() === that.$t('types.glass','en').toLowerCase())  that.series[2].data[0]+=recycle.amount;
                if(recycle.type.toLowerCase() === that.$t('types.metal','en').toLowerCase())  that.series[3].data[0]+=recycle.amount;

              } 

                if(recycle.yearMonth.slice(-2) == "02") 
              {
                if(recycle.type.toLowerCase() === that.$t('types.paper','en').toLowerCase()) that.series[0].data[1]+=recycle.amount;
                if(recycle.type.toLowerCase() === that.$t('types.plastic','en').toLowerCase()) that.series[1].data[1]+=recycle.amount;
                if(recycle.type.toLowerCase() === that.$t('types.glass','en').toLowerCase())  that.series[2].data[1]+=recycle.amount;
                if(recycle.type.toLowerCase() === that.$t('types.metal','en').toLowerCase())  that.series[3].data[1]+=recycle.amount;

              } 

                if(recycle.yearMonth.slice(-2) == "03") 
              {
                if(recycle.type.toLowerCase() == that.$t('types.paper','en').toLowerCase()) that.series[0].data[2]+=recycle.amount
                if(recycle.type.toLowerCase() == that.$t('types.plastic','en').toLowerCase()) that.series[1].data[2]+=recycle.amount;
                if(recycle.type.toLowerCase() == that.$t('types.glass','en').toLowerCase())  that.series[2].data[2]+=recycle.amount;
                if(recycle.type.toLowerCase() == that.$t('types.metal','en').toLowerCase())  that.series[3].data[2]+=recycle.amount;

              } 

                if(recycle.yearMonth.slice(-2) == "04") 
              {

                if(recycle.type.toLowerCase() === that.$t('types.paper','en').toLowerCase()) that.series[0].data[3]+=recycle.amount;
                if(recycle.type.toLowerCase() === that.$t('types.plastic','en').toLowerCase()) that.series[1].data[3]+=recycle.amount;
                if(recycle.type.toLowerCase() === that.$t('types.glass','en').toLowerCase())  that.series[2].data[3]+=recycle.amount;
                if(recycle.type.toLowerCase() === that.$t('types.metal','en').toLowerCase())  that.series[3].data[3]+=recycle.amount;

              } 

                if(recycle.yearMonth.slice(-2) == "05") 
              {

                if(recycle.type.toLowerCase() === that.$t('types.paper','en').toLowerCase()) that.series[0].data[4]+=recycle.amount;
                if(recycle.type.toLowerCase() === that.$t('types.plastic','en').toLowerCase()) that.series[1].data[4]+=recycle.amount;
                if(recycle.type.toLowerCase() === that.$t('types.glass','en').toLowerCase())  that.series[2].data[4]+=recycle.amount;
                if(recycle.type.toLowerCase() === that.$t('types.metal','en').toLowerCase())  that.series[3].data[4]+=recycle.amount;

              } 

                if(recycle.yearMonth.slice(-2) == "06") 
              {

                if(recycle.type.toLowerCase() === that.$t('types.paper','en').toLowerCase()) that.series[0].data[5]+=recycle.amount;
                if(recycle.type.toLowerCase() === that.$t('types.plastic','en').toLowerCase()) that.series[1].data[5]+=recycle.amount;
                if(recycle.type.toLowerCase() === that.$t('types.glass','en').toLowerCase())  that.series[2].data[5]+=recycle.amount;
                if(recycle.type.toLowerCase() === that.$t('types.metal','en').toLowerCase())  that.series[3].data[5]+=recycle.amount;

              } 

                if(recycle.yearMonth.slice(-2) == "07") 
              {

                if(recycle.type.toLowerCase() === that.$t('types.paper','en').toLowerCase()) that.series[0].data[6]+=recycle.amount;
                if(recycle.type.toLowerCase() === that.$t('types.plastic','en').toLowerCase()) that.series[1].data[6]+=recycle.amount;
                if(recycle.type.toLowerCase() === that.$t('types.glass','en').toLowerCase())  that.series[2].data[6]+=recycle.amount;
                if(recycle.type.toLowerCase() === that.$t('types.metal','en').toLowerCase())  that.series[3].data[6]+=recycle.amount;

              } 

                if(recycle.yearMonth.slice(-2) == "08") 
              {

                if(recycle.type.toLowerCase() === that.$t('types.paper','en').toLowerCase()) that.series[0].data[7]+=recycle.amount;
                if(recycle.type.toLowerCase() === that.$t('types.plastic','en').toLowerCase()) that.series[1].data[7]+=recycle.amount;
                if(recycle.type.toLowerCase() === that.$t('types.glass','en').toLowerCase())  that.series[2].data[7]+=recycle.amount;
                if(recycle.type.toLowerCase() === that.$t('types.metal','en').toLowerCase())  that.series[3].data[7]+=recycle.amount;

              } 

                if(recycle.yearMonth.slice(-2) == "09") 
              {

                if(recycle.type.toLowerCase() === that.$t('types.paper','en').toLowerCase()) that.series[0].data[8]+=recycle.amount;
                if(recycle.type.toLowerCase() === that.$t('types.plastic','en').toLowerCase()) that.series[1].data[8]+=recycle.amount;
                if(recycle.type.toLowerCase() === that.$t('types.glass','en').toLowerCase())  that.series[2].data[8]+=recycle.amount;
                if(recycle.type.toLowerCase() === that.$t('types.metal','en').toLowerCase())  that.series[3].data[8]+=recycle.amount;

              } 

                if(recycle.yearMonth.slice(-2) == "10") 
              {

                if(recycle.type.toLowerCase() === that.$t('types.paper','en').toLowerCase()) that.series[0].data[9]+=recycle.amount;
                if(recycle.type.toLowerCase() === that.$t('types.plastic','en').toLowerCase()) that.series[1].data[9]+=recycle.amount;
                if(recycle.type.toLowerCase() === that.$t('types.glass','en').toLowerCase())  that.series[2].data[9]+=recycle.amount;
                if(recycle.type.toLowerCase() === that.$t('types.metal','en').toLowerCase())  that.series[3].data[9]+=recycle.amount;

              } 

                if(recycle.yearMonth.slice(-2) == "11") 
              {

                if(recycle.type.toLowerCase() === that.$t('types.paper','en').toLowerCase()) that.series[0].data[10]+=recycle.amount;
                if(recycle.type.toLowerCase() === that.$t('types.plastic','en').toLowerCase()) that.series[1].data[10]+=recycle.amount;
                if(recycle.type.toLowerCase() === that.$t('types.glass','en').toLowerCase())  that.series[2].data[10]+=recycle.amount;
                if(recycle.type.toLowerCase() === that.$t('types.metal','en').toLowerCase())  that.series[3].data[10]+=recycle.amount;

              } 

                if(recycle.yearMonth.slice(-2) == "12") 
              {

                if(recycle.type.toLowerCase() === that.$t('types.paper','en').toLowerCase()) that.series[0].data[11]+=recycle.amount;
                if(recycle.type.toLowerCase() === that.$t('types.plastic','en').toLowerCase()) that.series[1].data[11]+=recycle.amount;
                if(recycle.type.toLowerCase() === that.$t('types.glass','en').toLowerCase())  that.series[2].data[11]+=recycle.amount;
                if(recycle.type.toLowerCase() === that.$t('types.metal','en').toLowerCase())  that.series[3].data[11]+=recycle.amount;

              } 

            })

      

    },
    methods:
    {
        getRecycleData()
        {
            
            this.recycleData = [{type: this.$t('types.plastic'), amount:0},{type : this.$t('types.paper'), amount:0},{type: this.$t('types.glass'), amount:0},{type: this.$t('types.metal'), amount:0}]

            let yearMonth=this.selectedYear+"-"+this.to2digit(this.selectedMonth);
    

            let suma=0;
            let maks=0;
            let papir=0;
            let plastika=0;
            let staklo=0;
            let metal=0;

            if(yearMonth.slice(0,3) != this.$t('profile.all') && yearMonth.slice(-2) != this.$t('profile.all').slice(-2))
            this.allData.forEach(function(recycle)
            {
                if(yearMonth == recycle.yearMonth)
                {

                if(recycle.type.toLowerCase()==="paper") papir+=recycle.amount;
                if(recycle.type.toLowerCase()=="plastic") plastika+=recycle.amount;
                if(recycle.type.toLowerCase()=="glass")  staklo+=recycle.amount;
                if(recycle.type.toLowerCase()=="metal")  metal+=recycle.amount;

                suma+=recycle.amount
                if(recycle.amount>maks) maks=recycle.amount;
                }
            })
            
            if(yearMonth.slice(0,3) == this.$t('profile.all') && yearMonth.slice(-2) != this.$t('profile.all').slice(-2))
            this.allData.forEach(function(recycle)
            {
            
                if(yearMonth.slice(-2) == recycle.yearMonth.slice(-2))
                {

                if(recycle.type.toLowerCase()==="paper") papir+=recycle.amount;
                if(recycle.type.toLowerCase()=="plastic") plastika+=recycle.amount;
                if(recycle.type.toLowerCase()=="glass")  staklo+=recycle.amount;
                if(recycle.type.toLowerCase()=="metal")  metal+=recycle.amount;

                suma+=recycle.amount
                if(recycle.amount>maks) maks=recycle.amount;
                }
            })
      
            if(yearMonth.slice(0,3) == this.$t('profile.all') && yearMonth.slice(-2) == this.$t('profile.all').slice(-2))
            this.allData.forEach(function(recycle)
            {
           

                if(recycle.type.toLowerCase()==="paper") papir+=recycle.amount;
                if(recycle.type.toLowerCase()=="plastic") plastika+=recycle.amount;
                if(recycle.type.toLowerCase()=="glass")  staklo+=recycle.amount;
                if(recycle.type.toLowerCase()=="metal")  metal+=recycle.amount;

                suma+=recycle.amount
                if(recycle.amount>maks) maks=recycle.amount;
                
            })

            
            if(yearMonth.slice(0,3) != this.$t('profile.all') && yearMonth.slice(-2) == this.$t('profile.all').slice(-2))
            this.allData.forEach(function(recycle)
            {
                if(yearMonth.slice(0,4) == recycle.yearMonth.slice(0,4))
                {


                if(recycle.type.toLowerCase()==="paper") papir+=recycle.amount;
                if(recycle.type.toLowerCase()=="plastic") plastika+=recycle.amount;
                if(recycle.type.toLowerCase()=="glass")  staklo+=recycle.amount;
                if(recycle.type.toLowerCase()=="metal")  metal+=recycle.amount;

                suma+=recycle.amount
                if(recycle.amount>maks) maks=recycle.amount;
                }
            })

            this.recycleData[0].amount=plastika;
            this.recycleData[1].amount=papir;
            this.recycleData[2].amount=staklo;
            this.recycleData[3].amount=metal;

            this.max=maks;
            this.multiplier = 100/this.max;
            this.sum=suma;
            
        },
        yearChange()
        {
              if(this.selectedYear < (new Date().getFullYear())) for(let i=1;i<13;i++) this.months.push(i);
            else{
                let i = new Date().getMonth()+1;

                while(i>0)
                {
                    this.months.push(i)
                    i--
                }

                this.months.reverse();
            }

            this.getRecycleData();
        },
        to2digit(month)
        {
             return ('00' + month).slice(-2);
        }
    }
    
}
</script>
<style scoped>
</style>
