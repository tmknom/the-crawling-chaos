<template>
  <div id="contents">
    <el-tabs type="card" @tab-click="handle">
      <el-tab-pane v-bind:label="tabs.contribution">
        <ArticleList :articles="items"></ArticleList>
      </el-tab-pane>
      <el-tab-pane v-bind:label="tabs.hatena">
        <ArticleList :articles="items"></ArticleList>
      </el-tab-pane>
      <el-tab-pane v-bind:label="tabs.facebook">
        <ArticleList :articles="items"></ArticleList>
      </el-tab-pane>
      <el-tab-pane v-bind:label="tabs.pocket">
        <ArticleList :articles="items"></ArticleList>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
  // Vuex関連
  import {mapState, mapGetters, mapActions} from 'vuex';
  import ArticleList from './ArticleList';

  export default {
    name: 'Contents',
    components: {
      ArticleList
    },
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
        'fetchJson'
      ]),
      getTypeByLabel(label) {
        const tabs = this.$data.tabs;
        return Object.keys(tabs).filter((key) => {
          return tabs[key] === label
        }).shift();
      },
      handle(tab, event) {
        const jsonType = this.getTypeByLabel(tab.label);
        this.fetchJson({jsonType: jsonType});
      }
    },
    created() {
      this.fetchJson({jsonType: 'contribution'});
    }
  }
</script>
