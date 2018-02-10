import api from '../../api/articles';

export default {
  namespaced: true,

  state: {
    items: []
  },

  mutations: {
    replaceItems(state, items) {
      state.items = items
    }
  },

  actions: {
    fetchJson({commit}, payload) {
      api.request(payload.jsonType).then((result) => {
        commit('replaceItems', result)
      }).catch(function (error) {
        console.log(error);
      });
    }
  }
};
