import Vue from 'vue'
import VueRouter from 'vue-router'
import error from '../pages/error.vue'
import about from '../pages/about.vue'
import Articles from '../pages/articles.vue'
import Scouter from '../pages/scouter.vue'
import UserTotal from '../pages/users/total.vue'
import UserContribution from '../pages/users/contribution.vue'
import UserHatena from '../pages/users/hatena.vue'

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
    path: '/scouter',
    name: 'Scouter',
    component: Scouter
  },
  {
    path: '/users',
    name: 'UserTotal',
    component: UserTotal
  },
  {
    path: '/users/contribution',
    name: 'UserContribution',
    component: UserContribution
  },
  {
    path: '/users/hatena',
    name: 'UserHatena',
    component: UserHatena
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
