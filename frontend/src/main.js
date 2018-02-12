import Vue from 'vue'
import Buefy from 'buefy'
import 'buefy/lib/buefy.css'

import App from './App.vue'
import store from './store'
import router from './router'

Vue.use(Buefy, {defaultIconPack: 'fa'});

new Vue({
  el: '#app',
  store,
  router,
  render: h => h(App)
});
