<template>
  <div id="app">
    <el-container>
      <el-header>ヘッダーだよ</el-header>
      <el-container>
        <el-main>
          <Contents/>
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
  import Contents from './components/Contents.vue'

  export default {
    components: {
      Contents
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
