import Vue from 'vue'
import Vuex from 'vuex'
import http from "../http-common";

Vue.use(Vuex);

export const store = new Vuex.Store({

    state :
    {
        token: localStorage.getItem('token') || null,
        profile: localStorage.getItem('profile') || null
    },
    getters :
    {
        loggedIn(state)
        {
            //console.log("loggedIn "+state.token)
            return state.token!=null
        }

    },
    mutations :
    {
        retrieveToken(state, token)
        {
            //console.log("getter "+ token)
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
        },



    },
    actions :
    {
        destroyToken(context)
        {
            if(context.getters.loggedIn)
            {
                //console.log("remove token")
                localStorage.removeItem('token');
                localStorage.removeItem('profile');
                //console.log(localStorage.getItem('token'));
                context.commit('destroyToken');

            }
        },

        setToken(context, credentials)
        {
            return new Promise((resolve,reject) =>
            {
                http
                .post("/authenticate", credentials)
                .then(response => {
                    const token = "Bearer "+response.data.token;
                   // console.log("setter "+ token)
                    localStorage.setItem('token', token);
                    context.commit('retrieveToken', token);
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
                //console.log(http.defaults)
                console.log(this.state.token)
                http
                .get("/user/current-user")
                .then(response => {
                    console.log(response)
                    const profile = response.data;
                    console.log(profile)
                    localStorage.setItem('profile', profile);
                    context.commit('setProfile', profile);

                    resolve(response);
                })
                .catch(e => {
                console.log(e);
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

                console.log(response.data);
                resolve(response.data);

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
                console.log(data.user)

                http.put("/user", data.user )
                .then(response => {

                console.log(response.data);

               /* if(data.flag) */
               context.dispatch('destroyToken')

                /*const profile = response.data;
                localStorage.setItem('profile', profile);
                context.commit('setProfile', profile);*/

                resolve(response.data);

                })
                .catch(e => {
                console.log(e);
                reject(e)
                });

            })
        }

    }
})
