<template>
  <section id="contents" class="section">
    <h1 class="title">ユーザランキング</h1>

    <UserList :users="items"></UserList>
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
        'items'
      ])
    },
    methods: {
      ...mapActions('users', [
        'fetchJson'
      ]),
    },
    created() {
      this.fetchJson({index: 1});
    }
  }
</script>

<style lang="scss" scoped>
  /deep/ {
  }
</style>
