export default {
  namespaced: true,
  state: {
    items: []
  },
  mutations: {
    replaceItems(state, payload) {
      state.items = payload.items
    }
  },
  actions: {
    replaceItems({commit}, payload) {
      commit('replaceItems', payload)
    }
  }
};
