import Vue from 'vue'
import VueRouter from 'vue-router'

import index from '../pages/index.vue'
import about from '../pages/about.vue'

Vue.use(VueRouter);

const routes = [
  {path: '/', component: index},
  {path: '/about', component: about},
];

export default new VueRouter({routes});
