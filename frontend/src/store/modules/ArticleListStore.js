import api from '../../api/modules/ArticleListApi'

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
      api.request(payload.jsonType, payload.index).then((result) => {
        commit('replaceItems', result)
      }).catch(function (error) {
        console.log(error);
      });
    }
  }
};
