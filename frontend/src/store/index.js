import Vue from 'vue'
import Vuex from 'vuex'
import articles from './modules/articles'
import users from './modules/users'
import scouter from "./modules/scouter"

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    articles: articles,
    users: users,
    scouter: scouter,
  },
  // https://vuex.vuejs.org/ja/strict.html
  strict: process.env.NODE_ENV !== 'production'
});
