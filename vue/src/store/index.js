import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    userid:'',
    username:'',
    limitgrade:{},
    pro:{}
  },
  getters: {
  },
  mutations: {
    changeuserid(state,userid){
      state.userid=userid
    },
    changeusername(state,username){
      state.username=username
    },
    changeproid(state,proid){
      Vue.set(state.pro,'proid',proid)
    },
    changelimit(state,value){
      Vue.set(state.limitgrade,'limit',value)
    }
  },
  actions: {
  },
  modules: {
  }
})
