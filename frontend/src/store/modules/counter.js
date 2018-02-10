export default {
  namespaced: true,
  state: {
    count: 0
  },
  getters: {
    double: state => {
      return state.count * 2
    }
  },
  mutations: {
    increment(state, payload) {
      state.count += payload.amount
    }
  },
  actions: {
    increment({commit}, payload) {
      commit('increment', payload)
    }
  }
};
