import api from '../../api/articles';

export default {
  namespaced: true,
  state: {
    items: []
  },
  mutations: {
    fetchJson(state, payload) {
      api.request(payload.jsonType).then((result) => {
        state.items = result;
      }).catch(function (error) {
        console.log(error);
      });
    },
    replaceItems(state, payload) {
      state.items = payload.items
    }
  },
  actions: {
    fetchJson({commit}, payload) {
      commit('fetchJson', payload)
    },
    replaceItems({commit}, payload) {
      commit('replaceItems', payload)
    }
  }
};
