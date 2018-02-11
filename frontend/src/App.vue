<template>
  <div id="app" class="container is-fluid">
    <Header></Header>
    <Contents></Contents>
    <Footer></Footer>
  </div>
</template>

<script>
  import Header from './components/layout/Header.vue'
  import Footer from './components/layout/Footer.vue'
  import Contents from './components/Contents.vue'

  export default {
    components: {
      Header,
      Footer,
      Contents
    }
  }
</script>
