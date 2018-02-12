import Vue from 'vue'
import VueRouter from 'vue-router'

import index from '../pages/index.vue'
import error from '../pages/error.vue'
import about from '../pages/about.vue'

Vue.use(VueRouter);

const routes = [
  {path: '/', component: index},
  {path: '/about', component: about},

  // エラーページなので、必ず最後に記述する
  // https://router.vuejs.org/ja/essentials/history-mode.html
  {path: '*', component: error}
];

export default new VueRouter({mode: 'history', routes: routes});
