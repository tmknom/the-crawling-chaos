import api from '../../api/modules/UserListApi'

export default {
  namespaced: true,

  state: {
    totals: [],
    articles_counts: [],
  },

  mutations: {
    replaceTotals(state, totals) {
      state.totals = totals
    },
    replaceArticlesCounts(state, articles_counts) {
      state.articles_counts = articles_counts
    }
  },

  actions: {
    fetchTotals({commit}, payload) {
      api.request('total', payload.index).then((result) => {
        commit('replaceTotals', result)
      }).catch(function (error) {
        console.log(error);
      });
    },
    fetchArticlesCounts({commit}, payload) {
      api.request('articles_count', payload.index).then((result) => {
        commit('replaceArticlesCounts', result)
      }).catch(function (error) {
        console.log(error);
      });
    }
  }
};
