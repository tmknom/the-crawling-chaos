import Vue from 'vue'
import Vuex from 'vuex'

import counter from './modules/counter'
import articles from './modules/articles'
import users from './modules/users'
import userProfile from "./modules/userProfile";

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    counter: counter,
    articles: articles,
    users: users,
    userProfile: userProfile,
  },
  // https://vuex.vuejs.org/ja/strict.html
  strict: process.env.NODE_ENV !== 'production'
});
