<template>
  <div id="hello">
    <div id="articles" v-for="result in results">
      <div class="card">
        <div class="card-divider">
          {{ result.index }}：{{ result.name }}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  // Ajax通信ライブラリ
  import axios from 'axios';

  export default {
    name: 'Hello',
    data() {
      return {
        results: []
      }
    },
    methods: {
      get_ajax_users() {
        return axios.get('http://temporary-7037dee17452.s3-website-ap-northeast-1.amazonaws.com/qiita-ranker/user_contribution/user.article.1.json')
          .then(function (response) {
            return response.data;
          })
          .catch(function (error) {
            console.log(error);
            return [];
          });
      }
    },
    created() {
      this.get_ajax_users().then((result) => {
        this.$data.results = result;
      }).catch(function (error) {
        console.log(error);
      });
    }
  }
</script>

<style lang="scss">
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin-top: 60px;
  }

  h1, h2 {
    font-weight: normal;
  }

  ul {
    list-style-type: none;
    padding: 0;
  }

  li {
    display: inline-block;
    margin: 0 10px;
  }

  a {
    color: #42b983;
  }
</style>
