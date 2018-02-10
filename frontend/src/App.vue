<template>
  <div id="app">
    <el-container>
      <el-header>ヘッダーだよ</el-header>
      <el-container>
        <el-main>
          <Hello/>
        </el-main>
        <el-aside>
          サイドメニュー
        </el-aside>
      </el-container>
      <el-footer>Footer</el-footer>
    </el-container>
  </div>
</template>

<script>
  import Hello from './Hello.vue'

  export default {
    components: {
      Hello
    }
  }
</script>

<style lang="scss" scoped>
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin: 0;
  }
</style>
