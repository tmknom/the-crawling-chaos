import api from '../../api/modules/UserListApi'

export default {
  namespaced: true,

  state: {
    counts: [],
    averages: [],
  },

  mutations: {
    replaceCounts(state, counts) {
      state.counts = counts
    },
    replaceAverages(state, averages) {
      state.averages = averages
    }
  },

  actions: {
    fetchCounts({commit}, payload) {
      api.request('hatena_count', payload.index).then((result) => {
        commit('replaceCounts', result)
      }).catch(function (error) {
        console.log(error);
      });
    },
    fetchAverages({commit}, payload) {
      api.request('hatena_count_average', payload.index).then((result) => {
        commit('replaceAverages', result)
      }).catch(function (error) {
        console.log(error);
      });
    }
  }
};
