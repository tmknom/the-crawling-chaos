<template>
  <section id="contents" class="section">
    <h1 class="title">Section</h1>
    <h2 class="subtitle">
      A simple container to divide your page into <strong>sections</strong>, like the one you're currently reading
    </h2>

    <b-tabs type="is-boxed" @change="handle" expanded>
      <b-tab-item :label="tabs.contribution" icon-pack="fab" icon="qiita">
        <ArticleList :articles="items"></ArticleList>
      </b-tab-item>
      <b-tab-item :label="tabs.hatena" icon-pack="fab" icon="hatena">
        <ArticleList :articles="items"></ArticleList>
      </b-tab-item>
      <b-tab-item :label="tabs.facebook" icon-pack="fab" icon="facebook">
        <ArticleList :articles="items"></ArticleList>
      </b-tab-item>
      <b-tab-item :label="tabs.pocket" icon-pack="fab" icon="get-pocket">
        <ArticleList :articles="items"></ArticleList>
      </b-tab-item>
    </b-tabs>
  </section>
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
          'contribution': 'いいね！',
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
      getTypeByLabel(index) {
        return Object.keys(this.$data.tabs)[index];
      },
      handle(index) {
        const jsonType = this.getTypeByLabel(index);
        this.fetchJson({jsonType: jsonType});
      }
    },
    created() {
      this.fetchJson({jsonType: 'contribution'});
    }
  }
</script>

<style lang="scss" scoped>
  .subtitle {
    margin-bottom: 30px;
  }

  /deep/ {
    /* http://hayashikejinan.com/webwork/css/913/ */
    .fa-hatena {
      &:before {
        content: "B!";
        font-family: Verdana;
        font-weight: bold;
      }
    }

    .fa-qiita {
      &:before {
        content: "Q";
        font-family: Verdana;
        font-weight: bold;
      }
    }
  }
</style>
