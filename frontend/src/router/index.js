import Vue from 'vue'
import VueRouter from 'vue-router'
import error from '../pages/error.vue'
import about from '../pages/about.vue'
import Articles from '../pages/articles.vue'
import RankingUsers from '../pages/ranking/users.vue'
import UsersSearch from '../pages/users/search.vue'

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    name: 'Index',
    component: UsersSearch
  },
  {
    path: '/about',
    name: 'About',
    component: about
  },
  {
    path: '/articles',
    name: 'Articles',
    component: Articles
  },
  {
    path: '/ranking/users',
    name: 'RankingUsers',
    component: RankingUsers
  },
  {
    path: '/users/search',
    name: 'UsersSearch',
    component: UsersSearch
  },

  // エラーページなので、必ず最後に記述する
  // https://router.vuejs.org/ja/essentials/history-mode.html
  {
    path: '*',
    name: 'Error',
    component: error
  }
];

export default new VueRouter({mode: 'history', routes: routes});
