import Vue from 'vue'
import Vuex from 'vuex'
import http from "../http-common";
import createPersistedState from "vuex-persistedstate";
import axios from "axios"
import router from '../router/index'

Vue.use(Vuex);

export const store = new Vuex.Store({

    plugins: [createPersistedState()],

    state :
    {
        token: localStorage.getItem('token') || null,
        profile: localStorage.getItem('profile') || null,
    },
    getters :
    {
        loggedIn(state)
        {
            //console.log("loggedIn "+state.token)
            return state.token!=null
        },

        isAdmin(state)
        {
           return state.profile.authorities.includes("ROLE_ADMIN") ? true : false
        }

    },
    mutations :
    {
        retrieveToken(state, token)
        {
            //console.log(token)
            state.token= token;
        },
        destroyToken(state)
        {
            //console.log("remove token mutation")
            state.token= null;
            state.profile=null;
        },
        setProfile(state, profile)
        {
            state.profile=profile;
           // console.log("profile set "+profile)
        },



    },
    actions :
    {
        destroyToken(context)
        {
                //console.log("remove token")
                localStorage.removeItem('token');
                localStorage.removeItem('profile');
                http.defaults.headers['Authorization'] = null
                //console.log(localStorage.getItem('token'));
                context.commit('destroyToken');

            
        },

        setToken(context, credentials)
        {
            return new Promise((resolve,reject) =>
            {
                http
                .post("/authenticate", credentials)
                .then(response => {
                    const token = "Bearer "+response.data.token;
                  //  console.log("setter "+ token)
                    localStorage.setItem('token', token);
                    context.commit('retrieveToken', token);
                    http.defaults.headers['Authorization'] = token
                    //console.log("state token: "+this.state.token)
                    resolve(response);
                })
                .catch(e => {
                console.log(e);
                reject(e)
                });
            })
        },

        getProfile(context)
        {
            return new Promise((resolve,reject) =>
            {
               // console.log(http.defaults)
               // console.log(this.state.token)
                http
                .get("/user/current-user")
                .then(response => {
                   // console.log(response)
                    const profile = response.data;
                  //  console.log(profile)
                    localStorage.setItem('profile', profile);
                    context.commit('setProfile', profile);

                    resolve(response);
                })
                .catch(e => {
                console.log(e);
                localStorage.removeItem("token")
                reject(e)
                });
            })
        },

        getSchedule()
        {
            return new Promise((resolve,reject) =>
            {
                http
                .get("/schedule")
                .then(response => {

              //  console.log(response.data);
                resolve(response);

                })
                .catch(e => {
                console.log(e);
                reject(e)
                });

            })
        },

        addSchedule(context, data)
        {
            return new Promise((resolve,reject) =>
            {
                http
                .post("/schedule",data)
                .then(response => {

                resolve(response);

                })
                .catch(e => {
                console.log(e);
                reject(e)
                });

            })
        },

        
        editSchedule(context,data)
        {
            return new Promise((resolve,reject) =>
            {

                http
                .put("/schedule",data)
                .then(response => {

                resolve(response);

                })
                .catch(e => {
                console.log(e);
                reject(e)
                });

            })
        },

        
        deleteSchedule(context, data)
        {
            return new Promise((resolve,reject) =>
            {
                
                http
                .delete(`/schedule/${data}`)
                .then(response => {

                resolve(response);

                })
                .catch(e => {
                console.log(e);
                reject(e)
                });

            })
        },


        editUser(context,data)
        {
        return new Promise((resolve,reject) =>
            {
                console.log(data)

                http.put("/user", data )
                .then(response => {

                console.log(response.data);

               localStorage.removeItem('token');
               localStorage.removeItem('profile');
               context.dispatch('destroyToken')
               
                resolve(response.data);

                })
                .catch(e => {
                console.log(e);
                reject(e)
                });

            })
        },
        getUsers()
        {
            return new Promise((resolve,reject) =>
            {
                http.get("/user")
                .then(response =>
                    {
                        //console.log(response)
                        resolve(response)
                    })
                    .catch(e => {
                        console.log(e);
                        reject(e)
                        });
            })
        },
        getUserProfile(context,username)
        {
            return new Promise((resolve,reject) =>
            {
               
                http
                .get("/user/"+username)
                .then(response => {
                    resolve(response.data);
                })
                .catch(e => {  
                console.log(e);
                reject(e)
                });
            })
        },

        editUserFromAdmin(context,data)
        {
        return new Promise((resolve,reject) =>
            {
                http.put("/user", data )
                .then(response => {


               if(data.id === this.state.profile.id && (data.username != this.state.profile.username || 'dont change' != data.password))
               {
               localStorage.removeItem('token');
               localStorage.removeItem('profile');
               context.dispatch('destroyToken');
               router.push('/')
               }

               localStorage.setItem('profile', response.data);
               context.commit('setProfile', response.data);
               
                resolve(response);

                })
                .catch(e => {
                console.log(e);
                reject(e)
                });

            })
        },

        editUserRecycleData(context,data)
        {
        return new Promise((resolve,reject) =>
            {
                http.put("/user", data )
                .then(response => {


              if(data.id === this.state.profile.id)
               {
                /*localStorage.removeItem('token');
                localStorage.removeItem('profile');
                context.dispatch('destroyToken');
                router.push('/login')*/
                localStorage.setItem('profile', response.data);
                context.commit('setProfile', response.data);
               }
               
                resolve(response);

                })
                .catch(e => {
                console.log(e);
                reject(e)
                });

            })
        },

        makeAdmin(context, data)
        {
            console.log(data)

            return new Promise((resolve,reject) =>
            {
                http.put(`/user/makeAdmin/${data}`)
                .then(response => {

                    resolve(response)

            })
            .catch(e => {
                reject(e)
                });
            
            })
          

        },

        deleteUser(context, data)
        {
            return new Promise((resolve,reject) =>
            {
                http.delete("/user/"+data)
                .then(response => {

                    resolve(response)

            })
            .catch(e => {
                reject(e)
                });
            
            })
            
        },

        addUser(context, data)
        {
            return new Promise((resolve,reject) =>
        {
            http.post("/user", data)
            .then(response => {
                
                 resolve(response);
 
                 })
                 .catch(e => {
                 reject(e)
                 });
          })

        },

        getVehicles()
        {
            return new Promise((resolve,reject) =>
        {
            http.get("/vehicle" )
            .then(response => {
                
                 resolve(response);
 
                 })
                 .catch(e => {
                 reject(e)
                 });
          })

        },

        
        addVehicle(context, data)
        {
            return new Promise((resolve,reject) =>
        {
            http.post("/vehicle", data )
            .then(response => {
                
                 resolve(response);
 
                 })
                 .catch(e => {
                 reject(e)
                 });
          })

        },

        
        editVehicle(context, data)
        {
            return new Promise((resolve,reject) =>
        {
            http.put("/vehicle", data )
            .then(response => {
                
                 resolve(response);
 
                 })
                 .catch(e => {
                 reject(e)
                 });
          })

        },


        
        deleteVehicle(context, data)
        {
            return new Promise((resolve,reject) =>
        {
            http.delete(`/vehicle/${data}` )
            .then(response => {
                
                 resolve(response);
 
                 })
                 .catch(e => {
                 reject(e)
                 });
          })

        },

        getLongitudeAndLatitude(context,data)
        {
            return new Promise((resolve,reject) =>
            {
            axios
            .get(
              `https://maps.googleapis.com/maps/api/geocode/json?address=${data},+Zagreb,+HR&key=AIzaSyAIFsjNZOTpPJpJ9rQei3Y8F2gAJ6OQoR4`)
              .then(response => {
                
                resolve(response);

                })
                .catch(e => {
                reject(e)
                });
            })
        },

        getAnnouncements()
        {
            return new Promise((resolve,reject) =>
            {
                http
                .get("/announcement")
                .then(response => {

                resolve(response);

                })
                .catch(e => {
                console.log(e);
                reject(e)
                });

            })
        },

         
        addAnnouncement(context, data)
        {
            return new Promise((resolve,reject) =>
        {
            http.post("/announcement", data )
            .then(response => {
                
                 resolve(response);
 
                 })
                 .catch(e => {
                 reject(e)
                 });
          })

        },

        
        editAnnouncement(context, data)
        {
            return new Promise((resolve,reject) =>
        {
            http.put("/announcement", data )
            .then(response => {
                
                 resolve(response);
 
                 })
                 .catch(e => {
                 reject(e)
                 });
          })

        },


        
        deleteAnnouncement(context, data)
        {
            return new Promise((resolve,reject) =>
        {
            http.delete(`/announcement/${data}` )
            .then(response => {
                
                 resolve(response);
 
                 })
                 .catch(e => {
                 reject(e)
                 });
          })

        },


    }
})
