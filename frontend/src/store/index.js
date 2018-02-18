import Vue from 'vue'
import Vuex from 'vuex'
import articles from './modules/ArticleListStore'
import users from './modules/UserListStore'
import scouter from "./modules/UserStore"

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
