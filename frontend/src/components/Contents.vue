<template>
  <div id="hello">
    <p>カウント: {{ count }} × 2 = {{ double }}</p>

    <el-tabs type="card" @tab-click="handleClick">
      <el-tab-pane label="User">User</el-tab-pane>
      <el-tab-pane label="Config">Config</el-tab-pane>
      <el-tab-pane label="Role">Role</el-tab-pane>
      <el-tab-pane label="Task">Task</el-tab-pane>
    </el-tabs>

    <ul id="articles">
      <li class="article" v-for="result in results">
        {{ result.index }}：<a v-bind:href="result.article.url">{{ result.article.name }}</a>
        {{ result.contribution.likes_count }}
      </li>
    </ul>
  </div>
</template>

<script>
  // Ajax通信ライブラリ
  import axios from 'axios';
  // Vuex関連
  import {mapState, mapGetters, mapActions} from 'vuex';

  export default {
    name: 'Hello',
    computed: {
      ...mapState('counter', [
        'count'
      ]),
      ...mapGetters('counter', [
        'double',
      ])
    },
    data() {
      return {
        results: []
      }
    },
    methods: {
      ...mapActions('counter', [
        'increment'
      ]),
      get_ajax(path) {
        const baseUrl = 'http://temporary-7037dee17452.s3-website-ap-northeast-1.amazonaws.com';
        const url = baseUrl + '/qiita-ranker' + path;
        const request = async (_url) => {
          const response = await axios.get(_url);
          return response.data;
        };
        return request(url);
      },
      render_ajax(type) {
        this.get_ajax('/article/article.' + type + '.json.gz').then((result) => {
          this.$data.results = result;
        }).catch(function (error) {
          console.log(error);
        });
      },
      handleClick(tab, event) {
        this.increment({amount: 1000});
        console.log(this.count);
        switch (tab.label) {
          case "User":
            this.render_ajax('contribution');
            break;
          case "Config":
            this.render_ajax('hatena');
            break;
          case "Role":
            this.render_ajax('facebook');
            break;
          case "Task":
            this.render_ajax('pocket');
            break;
        }
      }
    },
    created() {
      this.render_ajax('contribution');
    }
  }
</script>

<style lang="scss">
  ul {
    list-style-type: none;
    padding: 0;
  }

  li {
    margin: 0 10px;
    text-align: left;
  }

  a {
    color: #42b983;
  }
</style>
