<template>
  <section id="contents" class="section">
    <h1 class="title">ユーザランキング</h1>

    <div class="columns">
      <div class="column">
        <div class="card">
          <div class="card-header">
            <div class="card-header-title">
              <h2 class="subtitle">いいね！ランキング</h2>
            </div>
          </div>
          <UserList :items="contributions"></UserList>
        </div>
      </div>
      <div class="column">
        <div class="card">
          <div class="card-header">
            <div class="card-header-title">
              <h2 class="subtitle">投稿数ランキング</h2>
            </div>
          </div>
          <UserList :items="articles_counts"></UserList>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
  import {mapState, mapActions} from 'vuex';
  import UserList from '../../components/UserList';

  export default {
    name: 'Contents',
    components: {
      UserList
    },
    computed: {
      ...mapState('users', [
        'contributions',
        'articles_counts',
      ])
    },
    methods: {
      ...mapActions('users', [
        'fetchContributions',
        'fetchArticlesCounts'
      ]),
    },
    created() {
      this.fetchContributions({index: 1});
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

  .subtitle {
    font-weight: bold;
  }
</style>
