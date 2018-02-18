import Vue from 'vue'
import VueRouter from 'vue-router'
import error from '../pages/error.vue'
import about from '../pages/about.vue'
import Articles from '../pages/articles.vue'
import Users from '../pages/users.vue'
import Scouter from '../pages/scouter.vue'

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    name: 'Index',
    component: Scouter
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
    path: '/users',
    name: 'Users',
    component: Users
  },
  {
    path: '/scouter',
    name: 'Scouter',
    component: Scouter
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
