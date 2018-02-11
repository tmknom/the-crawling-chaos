<template>
  <section id="contents" class="section">
    <div class="container">
      <section id="contents_header" class="section">
        <!--https://qiita.com/hkusu/items/fda8d8178dd693f95f3c-->
        <span class="icon has-text-info">
          <i class="fas fa-info-circle"></i>
        </span>
        <span class="fa-stack text-qiita">
          <i class="fa fa-square fa-stack-2x"></i>
          <i class="fa fa-stack-1x fa-inverse fa-search fa-2x"></i>
        </span>
        <span class="fa-stack text-twitter">
          <i class="fas fa-square fa-stack-2x"></i>
          <i class="fab fa-stack-1x fa-inverse fa-twitter"></i>
          </span>
        <span class="fa-stack text-facebook">
          <i class="fas fa-square fa-stack-2x"></i>
          <i class="fab fa-stack-1x fa-inverse fa-facebook"></i>
        </span>
        <span class="fa-stack text-get-pocket">
          <i class="fas fa-square fa-stack-2x"></i>
          <i class="fab fa-stack-1x fa-inverse fa-get-pocket"></i>
        </span>

        <h1 class="title">Section</h1>
        <h2 class="subtitle">
          A simple container to divide your page into <strong>sections</strong>, like the one you're currently reading
        </h2>
      </section>
    </div>

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
  .text-qiita {
    color: #4cb10d;
  }

  .text-twitter {
    color: #55acee;
  }

  .text-facebook {
    color: #3b5998;
  }

  .text-get-pocket {
    color: #ee4256;
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
