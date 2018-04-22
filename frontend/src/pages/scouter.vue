<template>
  <section class="section">
    <h1 class="title">Qiita戦闘力計測</h1>
    <div class="columns">
      <div class="column is-8">
        <b-input v-model="userName"
                 @keyup.native.enter="handle"
                 placeholder="Qiitaのユーザ名を入力してください。@は不要です。"
                 type="search"
                 size="is-large"
                 icon-pack="fas"
                 icon="search">
        </b-input>
      </div>
      <div class="column is-4">
        <a @click="handle" class="button is-primary is-large is-outlined">
          <span class="icon is-medium">
            <i class="fas fa-search"></i>
          </span>
          <span>戦闘力を計測する！</span>
        </a>
      </div>
    </div>

    <div class="columns">
      <div class="column is-4">
        <UserCard :user="user"></UserCard>
      </div>

      <div class="column is-8">
        <UserMessage :user="user"></UserMessage>
      </div>
    </div>

  </section>
</template>

<script>
  import {mapState, mapActions} from 'vuex'
  import UserCard from '../components/UserCard'
  import UserMessage from '../components/UserMessage'

  export default {
    name: 'Scouter',
    components: {
      UserCard,
      UserMessage
    },
    data() {
      return {
        userName: ''
      }
    },
    computed: {
      ...mapState('UserStore', [
        'user',
      ])
    },
    methods: {
      ...mapActions('UserStore', [
        'fetchUser',
      ]),
      searchUser(name) {
        this.fetchUser({name: name});
      },
      handle() {
        this.searchUser(this.$data.userName);
      },
    }
  }
</script>

<style lang="scss" scoped>
</style>
