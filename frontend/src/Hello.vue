<template>
  <div id="hello">
    <div id="articles" v-for="result in results">
      <div class="card">
        <div class="card-divider">
          {{ result.permanent_id }}：{{ result.id }}
        </div>
        <div class="card-section">
          <img v-bind:src="result.profile_image_url"/>
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
        return axios.get('https://qiita.com/api/v2/users')
          .then(function (response) {
            console.log(response.data);
            return response.data;
          })
          .catch(function (error) {
            console.log(error);
            return [];
          });
      }
    },
    created() {
      console.log(this.results);
      this.results = this.get_ajax_users();
      console.log(this.results);
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
