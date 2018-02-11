import Vue from 'vue'
import Buefy from 'buefy'
import 'buefy/lib/buefy.css'

import App from './App.vue'
import store from './store'

Vue.use(Buefy, {defaultIconPack: 'fa'});

new Vue({
  el: '#app',
  store,
  render: h => h(App)
});
