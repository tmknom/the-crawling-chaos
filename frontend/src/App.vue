<template>
  <div id="app" class="container is-fluid">
    <Header></Header>
    <router-view></router-view>
    <Footer></Footer>
  </div>
</template>

<script>
  import Header from './components/layout/Header.vue'
  import Footer from './layouts/Footer.vue'

  export default {
    components: {
      Header,
      Footer
    }
  }
</script>
