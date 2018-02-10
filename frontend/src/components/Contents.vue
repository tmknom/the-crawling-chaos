<template>
  <div id="hello">
    <el-tabs type="card" @tab-click="handleClick">
      <el-tab-pane v-bind:label="tabs.contribution">User</el-tab-pane>
      <el-tab-pane v-bind:label="tabs.hatena">Config</el-tab-pane>
      <el-tab-pane v-bind:label="tabs.facebook">Role</el-tab-pane>
      <el-tab-pane v-bind:label="tabs.pocket">Task</el-tab-pane>
    </el-tabs>

    <ul id="articles">
      <li class="article" v-for="result in items">
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
    data() {
      return {
        tabs: {
          'contribution': 'いいね',
          'hatena': 'はてブ',
          'facebook': 'Facebook',
          'pocket': 'Pocket',

        }
      }
    },
    computed: {
      ...mapState('articles', [
        'items'
      ])
    },
    methods: {
      ...mapActions('articles', [
        'replaceItems'
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
          this.replaceItems({items: result});
        }).catch(function (error) {
          console.log(error);
        });
      },
      getTypeByLabel(label) {
        const tabs = this.$data.tabs;
        return Object.keys(tabs).filter((key) => {
          return tabs[key] === label
        }).shift();
      },
      handleClick(tab, event) {
        const type = this.getTypeByLabel(tab.label);
        this.render_ajax(type);
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
