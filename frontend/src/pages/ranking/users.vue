<template>
  <section id="contents" class="section">
    <h1 class="title">ユーザランキング</h1>

    <UserList :contributions="contributions" :articles_counts="articles_counts"></UserList>
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
  /deep/ {
  }
</style>
