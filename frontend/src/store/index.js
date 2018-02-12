import Vue from 'vue'
import Vuex from 'vuex'

import counter from './modules/counter'
import articles from './modules/articles'
import users from './modules/users'

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    counter: counter,
    articles: articles,
    users: users,
  },
  // https://vuex.vuejs.org/ja/strict.html
  strict: process.env.NODE_ENV !== 'production'
});
