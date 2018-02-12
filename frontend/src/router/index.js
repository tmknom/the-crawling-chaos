import Vue from 'vue'
import VueRouter from 'vue-router'

import about from '../pages/about.vue'

Vue.use(VueRouter);

const routes = [
  {path: '/about', component: about},
];

export default new VueRouter({routes});
