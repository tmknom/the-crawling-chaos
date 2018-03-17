<template>
  <section class="section">
    <h1 class="title">はてブランキング</h1>
    <div class="content">
      <h5>
        『打率』＝「はてなブックマーク数」／「投稿数」
        （なお、規定打席は投稿数10以上としている）
      </h5>
    </div>

    <div class="columns">
      <div class="column">
        <div class="card">
          <div class="card-header">
            <div class="card-header-title">
              <h2 class="subtitle">はてなブックマーク数</h2>
            </div>
          </div>
          <UserList :items="counts"></UserList>
        </div>

        <b-pagination
          :total="pagination.total.counts"
          :per-page="pagination.perPage"
          :order="pagination.order"
          :current.sync="pagination.current.counts"
          @change="pageChangeCounts">
        </b-pagination>
      </div>

      <div class="column">
        <div class="card">
          <div class="card-header">
            <div class="card-header-title">
              <h2 class="subtitle">はてなブックマーク打率</h2>
            </div>
          </div>
          <UserList :items="averages"></UserList>
        </div>

        <b-pagination
          :total="pagination.total.averages"
          :per-page="pagination.perPage"
          :order="pagination.order"
          :current.sync="pagination.current.averages"
          @change="pageChangeAverages">
        </b-pagination>
      </div>
    </div>
  </section>
</template>

<script>
  import {mapState, mapActions} from 'vuex'
  import UserList from '../../components/UserList'

  export default {
    name: 'UserHatena',
    components: {
      UserList
    },
    data() {
      return {
        pagination: {
          total: {
            "counts": 30000,
            "averages": 6500,
          },
          perPage: 100,
          order: 'is-centered',
          current: {
            "counts": 1,
            "averages": 1,
          },
        }
      }
    },
    computed: {
      ...mapState('UserHatenaStore', [
        'counts',
        'averages',
      ])
    },
    methods: {
      ...mapActions('UserHatenaStore', [
        'fetchCounts',
        'fetchAverages'
      ]),
      pageChangeCounts(index) {
        this.fetchCounts({index: index});
      },
      pageChangeAverages(index) {
        this.fetchAverages({index: index});
      }
    },
    created() {
      this.fetchCounts({index: 1});
      this.fetchAverages({index: 1});
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
