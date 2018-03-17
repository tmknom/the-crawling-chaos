<template>
  <section class="section">
    <h1 class="title">総合ランキング</h1>
    <div class="content">
      <h5>
        『総合』＝「QiitaのContribution数」＋「はてなブックマーク数」
      </h5>
    </div>

    <div class="columns">
      <div class="column">
        <div class="card">
          <div class="card-header">
            <div class="card-header-title">
              <h2 class="subtitle">総合</h2>
            </div>
          </div>
          <UserList :items="totals"></UserList>
        </div>

        <b-pagination
          :total="pagination.total.totals"
          :per-page="pagination.perPage"
          :order="pagination.order"
          :current.sync="pagination.current.totals"
          @change="pageChangeTotals">
        </b-pagination>
      </div>

      <div class="column">
        <div class="card">
          <div class="card-header">
            <div class="card-header-title">
              <h2 class="subtitle">投稿数</h2>
            </div>
          </div>
          <UserList :items="articles_counts"></UserList>
        </div>

        <b-pagination
          :total="pagination.total.articles_counts"
          :per-page="pagination.perPage"
          :order="pagination.order"
          :current.sync="pagination.current.articles_counts"
          @change="pageChangeArticlesCounts">
        </b-pagination>
      </div>
    </div>
  </section>
</template>

<script>
  import {mapState, mapActions} from 'vuex'
  import UserList from '../../components/UserList'

  export default {
    name: 'Users',
    components: {
      UserList
    },
    data() {
      return {
        pagination: {
          total: {
            'totals': 30000,
            'articles_counts': 30000,
          },
          perPage: 100,
          order: 'is-centered',
          current: {
            'totals': 1,
            'articles_counts': 1,
          },
        }
      }
    },
    computed: {
      ...mapState('UserTotalStore', [
        'totals',
        'articles_counts',
      ])
    },
    methods: {
      ...mapActions('UserTotalStore', [
        'fetchTotals',
        'fetchArticlesCounts'
      ]),
      pageChangeTotals(index) {
        this.fetchTotals({index: index});
      },
      pageChangeArticlesCounts(index) {
        this.fetchArticlesCounts({index: index});
      }
    },
    created() {
      this.fetchTotals({index: 1});
      this.fetchArticlesCounts({index: 1});
    }
  }
</script>

<style lang="scss" scoped>
  .card {
    border-radius: 0.3em;
  }

  .card-header {
    border-bottom: solid 1px #dbdbdb;
  }

  .pagination {
    margin-top: 10px;
  }

  .subtitle {
    font-weight: bold;
  }
</style>
