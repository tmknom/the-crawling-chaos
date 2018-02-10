import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    count: 0
  },
  getters: {
    double: state => {
      return state.count * 2
    }
  },
  mutations: {
    increment(state) {
      state.count++
    }
  }
});
