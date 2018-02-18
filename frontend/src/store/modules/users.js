import api from '../../api/ranking/users'

export default {
  namespaced: true,

  state: {
    contributions: [],
    articles_counts: [],
  },

  mutations: {
    replaceContributions(state, contributions) {
      state.contributions = contributions
    },
    replaceArticlesCounts(state, articles_counts) {
      state.articles_counts = articles_counts
    }
  },

  actions: {
    fetchContributions({commit}, payload) {
      api.request('contribution', payload.index).then((result) => {
        commit('replaceContributions', result)
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
