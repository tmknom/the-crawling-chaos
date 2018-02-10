import Vue from 'vue'
import Vuex from 'vuex'

import counter from './modules/counter'
import articles from './modules/articles'

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    counter: counter,
    articles: articles
  },
  // https://vuex.vuejs.org/ja/strict.html
  strict: process.env.NODE_ENV !== 'production'
});
