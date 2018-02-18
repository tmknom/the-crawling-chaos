import Vue from 'vue'
import Vuex from 'vuex'
import ArticleListStore from './modules/ArticleListStore'
import UserListStore from './modules/UserListStore'
import UserStore from "./modules/UserStore"

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    ArticleListStore: ArticleListStore,
    UserListStore: UserListStore,
    UserStore: UserStore,
  },
  // https://vuex.vuejs.org/ja/strict.html
  strict: process.env.NODE_ENV !== 'production'
});
