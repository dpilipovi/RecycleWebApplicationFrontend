<template>
<v-img height="93.2vh" src="../assets/images/profile_background4.jpg">
<div class="background">
<v-container >
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
    <v-col cols="4" align="end"><v-btn to="/editProfile">Edit profile</v-btn></v-col>
    </v-row>              
    <v-row >
       <!-- <v-col class="d-flex" cols="6"></v-col>-->
        <v-col class="d-flex" cols="2" tag="h1">
            <p class="font-weight-bold tamno-siva">Recycle history</p>
        </v-col>
    </v-row>
    <v-row >
        <!--<v-col class="d-flex" cols="6"></v-col>-->
        <v-col class="d-flex" cols="2" >
        <v-select
          :items="years"
          label="Year"
          dense
          solo
          v-model="selectedYear"
          @change="yearChange()"
        ></v-select>
      </v-col>
      <v-col class="d-flex" cols="2" >
        <v-select
          :items="months"
          label="Month"
          dense
          solo
          v-model="selectedMonth"
          @change="getRecycleData()"
        ></v-select>
      </v-col>
    </v-row>

    <v-row >
        <v-col cols="6" >
            <div  v-for="(r,index) in recycleData" :key="index">
                <!--<p><progress class="progress is-danger" :data-label="r.amount +'kg'" :value="r.amount" :max="max" ></progress>{{r.type.name}}</p>
                <br>-->
                  <span class="font-weight-medium">{{r.type}}</span>  <v-progress-linear  height="20px" rounded :value="r.amount*multiplier" color="grey darken-2" ><strong>{{r.amount}}kg</strong></v-progress-linear>
                    <br>
            </div>
        </v-col>
    </v-row>

</v-container>
 <!--
        <h2>POSTIGNUĆA:</h2>
        <div class="columns">
            <div class="column" v-for="(a,index) in achievementData" :key="index" >
                <p>{{a.type}}</p>
                <img :src="a.source">
                <div class="smanjen"><progress class="progress is-success" :value="a.amount" max="100"></progress> </div>
                </div>
        </div>
 -->
</div>
</v-img>
</template>
<script>

export default {
     data() {
      return {
        allData: this.$store.state.profile.user_recycle,
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
        multiplier: 0
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



            i = currentTime.getMonth()+1
            while(i>0)
            {
                this.months.push(i)
                i--
            }

            this.months.reverse();


        //let achievementData=[{type: "PLASTIKA", amount:0, source:"'../assets/images/plastic.jpeg'"},{type: "PAPIR", amount:0, source:"'../assets/images/paper.jpeg'"},{type: "STAKLO", amount:0, source:"'../assets/images/glass.jpeg'"},{type: "METAL", amount:0, source:"'../assets/images/metal.jpeg'"}]

      

    },
    methods:
    {
        getRecycleData()
        {
            this.defaultPage = false;

            this.recycleData = [{type:"Plastic", amount:0},{type :"Paper", amount:0},{type:"Glass", amount:0},{type:"Metal", amount:0}]

            let yearMonth=this.selectedYear+"-"+this.to2digit(this.selectedMonth);

            console.log(yearMonth)

            let suma=0;
            let maks=0;
            let papir=0;
            let plastika=0;
            let staklo=0;
            let metal=0;

            this.allData.forEach(function(recycle)
            {
                if(yearMonth == recycle.yearMonth)
                {
                console.log(recycle)


                if(recycle.type.toLowerCase()==="paper") papir+=recycle.amount;//this.recycleData[0].amount+=data.amount;
                if(recycle.type.toLowerCase()=="plastic") plastika+=recycle.amount;//this.recycleData[1].amount+=data.amount;
                if(recycle.type.toLowerCase()=="glass")  staklo+=recycle.amount;//this.recycleData[2].amount+=data.amount;
                if(recycle.type.toLowerCase()=="metal")  metal+=recycle.amount;//this.recycleData[3].amount+=data.amount;

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
            //console.log(this.max)
            this.sum=suma;
         console.log(this.recycleData)
            
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
p
{
    color:black;
    font-weight:bold;
}
progress {
  text-align: center;
  height: 1.4em;
  width: 100%;
  -webkit-appearance: none;
  border: none;

  /* Set the progressbar to relative */
  position:relative;
}
progress:before {
  content: attr(data-label);
  font-size: 0.8em;
  vertical-align: 0;

  /*Position text over the progress bar */
  position:absolute;
  left:0;
  right:0;
}
select
{
    background-color: #555555;
    opacity:80%;
    color:black;
    font-weight:bold;
    min-width:150px;
}
.field
{
    padding-right:10px;
}

.button
{
    color:black;
    font-weight: bold;
}
select:disabled
{
    background-color: #555555;
}
select title
{
    color:red;
}
img
{
    margin: 0 auto;
    width:200px;
    height:200px;
}
h2 {
color:black;
font-size:1.5em;
font-weight: bold;
}
.smanjen progress
{
    margin: 0 auto;
    height: 1em;
    max-width:200px;
}
.info
{
    font-size:1.7em;
}
.title
{
    color:black;
    font-weight: bold;
    font-size:2.5em;
}
.phone
{
    display:none;
}
.desktop-float-right
{
    float:right;
}
.background
{
    background-color: rgba(0,0,0,0.2);
    height: 93.2vh;
}
.background2
{
    background-color: rgba(128,128,128,0.05);
}
.tamno-siva
{
    color: #424242;
}

@media screen and (max-width: 768px){
    .phone
    {
        display:block;
      margin-top:1em;
    }
}
</style>
