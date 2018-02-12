<template>
  <section id="contents" class="section">
    <b-tabs type="is-boxed" @change="handle" expanded>
      <b-tab-item :label="tabs.qiita" icon-pack="fab" icon="qiita">
        <ArticleList :articles="items"></ArticleList>

        <b-pagination
          :total="pagination.total"
          :per-page="pagination.perPage"
          :order="pagination.order"
          :current.sync="pagination.current.qiita"
          @change="pageChange">
        </b-pagination>
      </b-tab-item>
      <b-tab-item :label="tabs.hatena" icon-pack="fab" icon="hatena">
        <ArticleList :articles="items"></ArticleList>

        <b-pagination
          :total="pagination.total"
          :per-page="pagination.perPage"
          :order="pagination.order"
          :current.sync="pagination.current.hatena"
          @change="pageChange">
        </b-pagination>
      </b-tab-item>
      <b-tab-item :label="tabs.facebook" icon-pack="fab" icon="facebook">
        <ArticleList :articles="items"></ArticleList>

        <b-pagination
          :total="pagination.total"
          :per-page="pagination.perPage"
          :order="pagination.order"
          :current.sync="pagination.current.facebook"
          @change="pageChange">
        </b-pagination>
      </b-tab-item>
      <b-tab-item :label="tabs.pocket" icon-pack="fab" icon="get-pocket">
        <ArticleList :articles="items"></ArticleList>

        <b-pagination
          :total="pagination.total"
          :per-page="pagination.perPage"
          :order="pagination.order"
          :current.sync="pagination.current.pocket"
          @change="pageChange">
        </b-pagination>
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
        currentJsonType: 'qiita',
        pagination: {
          total: 10000,
          perPage: 100,
          order: 'is-centered',
          current: {
            'qiita': 1,
            'hatena': 1,
            'facebook': 1,
            'pocket': 1,
          },
        },
        tabs: {
          'qiita': 'いいね！',
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
      fetch(index) {
        const jsonType = this.$data.currentJsonType;
        this.fetchJson({jsonType: jsonType, index: index});
      },
      handle(labelIndex) {
        const jsonType = this.getTypeByLabel(labelIndex);
        const index = this.$data.pagination.current[jsonType];
        this.$data.currentJsonType = jsonType;
        this.fetch(index);
      },
      pageChange(index) {
        this.fetch(index);
      }
    },
    created() {
      this.fetch(1);
    }
  }
</script>

<style lang="scss" scoped>
  .pagination {
    margin-top: 10px;
    margin-bottom: 10px;
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
