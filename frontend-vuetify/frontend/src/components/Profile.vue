<template>
<v-container>
    <v-col class="text-center" cols="12" tag="h1">
                        <span
                          class="font-weight-light"
                          :class="[
                            $vuetify.breakpoint.smAndDown
                              ? 'display-1'
                              : 'display-2',
                          ]"
                          >{{profile.firstname}} {{profile.lastname}}</span
                        >
    </v-col>
    <v-row>
        <v-col class="d-flex" cols="2" >
        <v-select
          :items="years"
          label="Year"
          dense
          solo
        ></v-select>
      </v-col>
      <v-col class="d-flex" cols="2" >
        <v-select
          :items="months"
          label="Month"
          dense
          solo
        ></v-select>
      </v-col>
    </v-row>

</v-container>
    <!-- <div class="hero-body">
     <div class="container has-text-centered" >
        <h1 class="title" style="display:inline">{{profile.firstname}} {{profile.lastname}}</h1>
        <div class="phone"></div>
        <router-link class="button is-danger is-rounded desktop-float-right" to="/editProfile" >Edit profile </router-link>
        <br>
        <br>
        <div class="columns">
        <div class="field">
        <div class="control">
            <div class="select is-danger is-rounded ">
            <select v-model="selectedYear" >
                <option :value="null" :disabled="true">Odaberite godinu</option>
                <option value="all">ALL</option>
                <option v-for="(year,index) in years" :value="year" :selected="year==currentYear" :key="index">{{year}}</option>
            </select>
            </div>
        </div>
        </div>
        <div class="field">
        <div class="control">
            <div class="select is-danger is-rounded">
            <select v-model="selectedMonth" :disabled="selectedYear==null">
                <option :value="null" :disabled="true">Odaberite mjesec</option>
                <option value="all">ALL</option>
                <option v-for="(month,index) in months" :value="month" :selected="month==currentMonth" :key="index" :disabled="selectedYear=='all'"  >{{month}}</option>
            </select>
            </div>
        </div>
        </div>
        <div class="phone"></div>
            <button class="button is-danger is-rounded"  @click="getRecycleData(selectedYear,selectedMonth)" :disabled="selectedMonth==null || selectedYear==null || (selectedYear=='all' && selectedMonth!='all') ">Pretraži</button>
        </div>
        <br>
        <div class="columns">
            <div class="column info" >
                <p :hidden="!defaultPage">UKUPNO PRIKUPLJENO PROŠLI MJESEC = {{sum}} kg</p>
                <p :hidden="defaultPage" v-if="selectedYear>0 && selectedMonth>0">UKUPNO PRIKUPLJENO {{selectedMonth}}. MJESEC {{selectedYear}}. = {{sum}} kg</p>
                <p :hidden="defaultPage" v-if="selectedYear>0 && selectedMonth=='all'">UKUPNO PRIKUPLJENO U {{selectedYear}}. GODINI = {{sum}} kg</p>
                <p :hidden="defaultPage" v-if="selectedMonth=='all' && selectedYear=='all'">UKUPNO PRIKUPLJENO = {{sum}} kg</p>
                <br>
            </div>
            <div class="column">
                <div  v-for="(r,index) in recycleData" :key="index">
                    <p><progress class="progress is-danger" :data-label="r.amount +'kg'" :value="r.amount" :max="max" ></progress>{{r.type.name}}</p>
                    <br>
                </div>
            </div>
        </div>
        <h2>POSTIGNUĆA:</h2>
        <div class="columns">
            <div class="column" v-for="(a,index) in achievementData" :key="index" >
                <p>{{a.type}}</p>
                <img :src="a.source">
                <div class="smanjen"><progress class="progress is-success" :value="a.amount" max="100"></progress> </div>
                </div>
        </div>


    </div>
    </div> -->
</template>
<script>

export default {
     data() {
      return {
        recycleData: [],
        sum: 0,
        max: 0,
        years : [],
        months : [],
        currentYear : 0,
        currentMonth : 0,
        selectedMonth : null,
        selectedYear : null,
        profile : this.$store.state.profile,
        defaultPage : true,
        achievementData: []
      }
    },
    created()
    {
        let suma=0;
        let maks=0;

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

      let data=
      {
          year: this.currentYear,
          month: this.currentMonth
      }

        this.$store.dispatch('getProfileDataForMonth',data )
        .then(response =>
        {
          this.recycleData = response;



            response.forEach(function(data)
             {
                //console.log(data.year +" "+ currentTime.getFullYear())
                //console.log(data.month +" "+ (currentTime.getMonth()+1))
                if(data.year == currentTime.getFullYear() && data.month == currentTime.getMonth()+1)
                {
                 suma+=data.amount
                 if(data.amount>maks) maks=data.amount;
                }

            })

            this.max=maks;
            //console.log(this.max)
            this.sum=suma;
           // console.log(this.sum)
            console.log(response)
        })


        let achievementData=[{type: "PLASTIKA", amount:0, source:"'../assets/images/plastic.jpeg'"},{type: "PAPIR", amount:0, source:"'../assets/images/paper.jpeg'"},{type: "STAKLO", amount:0, source:"'../assets/images/glass.jpeg'"},{type: "METAL", amount:0, source:"'../assets/images/metal.jpeg'"}]

        this.$store.dispatch('getProfileDataAll' )
        .then(response =>
        {
            console.log(response)


          response.forEach(function(data)
          {
              if(data.type.name=="Plastika") achievementData[0].amount+=data.amount;
              if(data.type.name=="Papir") achievementData[1].amount+=data.amount;
              if(data.type.name=="Staklo") achievementData[2].amount+=data.amount;
              if(data.type.name=="Metal") achievementData[3].amount+=data.amount;

          })

            this.achievementData=achievementData;
        })

    },
    methods:
    {
        getRecycleData(selectedYear,selectedMonth)
        {
            this.defaultPage = false;
            this.months= [];

            this.recycleData = [{type:{name:"Plastika"}, amount:0},{type :{name:"Papir"}, amount:0},{type:{name:"Staklo"}, amount:0},{type:{name:"Metal"}, amount:0}]

            let data=
            {
                year:this.selectedYear,
                month:this.selectedMonth
            }

            if(this.year < (new Date().getFullYear())) for(let i=1;i<13;i++) this.months.push(i);
            else{
                let i = new Date().getMonth()+1;

                while(i>0)
                {
                    this.months.push(i)
                    i--
                }

                this.months.reverse();

            }

            let suma=0;
            let maks=0;


            if(selectedMonth=="all" && selectedYear=="all") this.$store.dispatch('getProfileDataAll')
            .then(response =>
            {

                console.log(response)
                //this.recycleData=response;

                let papir=0;
                let plastika=0;
                let staklo=0;
                let metal=0;

                response.forEach(function(data)
                {
                    if(data.type.name=="Papir") papir+=data.amount;//this.recycleData[0].amount+=data.amount;
                    if(data.type.name=="Plastika") plastika+=data.amount;//this.recycleData[1].amount+=data.amount;
                    if(data.type.name=="Staklo")  staklo+=data.amount;//this.recycleData[2].amount+=data.amount;
                    if(data.type.name=="Metal")  metal+=data.amount;//this.recycleData[3].amount+=data.amount;

                    suma+=data.amount
                    if(data.amount>maks) maks=data.amount;
                })

                this.recycleData[0].amount=plastika;
                this.recycleData[1].amount=papir;
                this.recycleData[2].amount=staklo;
                this.recycleData[3].amount=metal;

                this.max=maks;
                //console.log(this.max)
                this.sum=suma;
            // console.log(this.sum)
                console.log(response)
            })

            if(selectedMonth=="all"  && selectedYear!="all" && selectedYear!=null) this.$store.dispatch('getProfileDataForYear', selectedYear)
            .then(response =>
            {
                console.log(response)
               // this.recycleData=response;
                 //this.recycleData = [{type:{name:"Papir"}, amount:0},{type :{name:"Plastika"}, amount:0},{type:{name:"Staklo"}, amount:0},{type:{name:"Metal"}, amount:0}]

                let papir=0;
                let plastika=0;
                let staklo=0;
                let metal=0;

                response.forEach(function(data)
                {
                     if(data.type.name=="Papir") papir+=data.amount;//this.recycleData[0].amount+=data.amount;
                    if(data.type.name=="Plastika") plastika+=data.amount;//this.recycleData[1].amount+=data.amount;
                    if(data.type.name=="Staklo")  staklo+=data.amount;//this.recycleData[2].amount+=data.amount;
                    if(data.type.name=="Metal")  metal+=data.amount;//this.recycleData[3].amount+=data.amount;

                    suma+=data.amount
                    if(data.amount>maks) maks=data.amount;
                })

                this.recycleData[0].amount=plastika;
                this.recycleData[1].amount=papir;
                this.recycleData[2].amount=staklo;
                this.recycleData[3].amount=metal;

                this.max=maks;
                //console.log(this.max)
                this.sum=suma;
                // console.log(this.sum)
                console.log(response)

             })

             if(selectedMonth!=null && selectedMonth!="all" && selectedYear!=null && selectedYear!="all") this.$store.dispatch('getProfileDataForMonth', data)
            .then(response =>
            {
                console.log(response)
                this.recycleData=response;

                response.forEach(function(data)
                {
                    suma+=data.amount
                    if(data.amount>maks) maks=data.amount;
                })

                this.max=maks;
                //console.log(this.max)
                this.sum=suma;
                // console.log(this.sum)
                console.log(response)

             })


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

@media screen and (max-width: 768px){
    .phone
    {
        display:block;
      margin-top:1em;
    }
}
</style>
