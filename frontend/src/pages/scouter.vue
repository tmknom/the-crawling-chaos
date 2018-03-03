<template>
  <section class="section">
    <h1 class="title">ユーザ検索</h1>
    <div class="columns">
      <div class="column is-6">
        <b-input v-model="userName"
                 @keyup.native.enter="handle"
                 placeholder="ユーザ名を入力してください"
                 type="search"
                 size="is-large"
                 icon-pack="fas"
                 icon="search">
        </b-input>
      </div>
      <div class="column is-2">
        <a @click="handle" class="button is-primary is-large is-outlined">
          <span class="icon is-medium">
            <i class="fas fa-search"></i>
          </span>
          <span>Push</span>
        </a>
      </div>
      <div class="column is-4">
        <div class="card" v-show="isCardVisible">
          <div class="card-content">
            <div class="media">
              <div class="media-left">
                <figure class="image is-48x48">
                  <a :href="qiitaUrl">
                    <img :src="user.profile_image_url" alt="Profile image">
                  </a>
                </figure>
              </div>
              <div class="media-content">
                <p class="title">総合 {{ user.rank }} 位</p>
                <p class="subtitle is-6"><a :href="qiitaUrl">@{{ user.name }}</a></p>
              </div>
            </div>

            <div class="content">
              <div class="level">
                <div class="level-item has-text-centered">
                  <div>
                    <p class="heading">総合評価</p>
                    <p class="title">{{ Number(user.total).toLocaleString() }}</p>
                  </div>
                </div>
                <div class="level-item has-text-centered">
                  <div>
                    <p class="heading">Qiita</p>
                    <p class="title">{{ Number(user.contribution).toLocaleString() }}</p>
                  </div>
                </div>
                <div class="level-item has-text-centered">
                  <div>
                    <p class="heading">はてブ</p>
                    <p class="title">{{ Number(user.hatena_count).toLocaleString() }}</p>
                  </div>
                </div>
              </div>
            </div>

            <div class="content">
              <div class="level">
                <div class="level-item has-text-centered">
                  <div>
                    <p class="heading">投稿数</p>
                    <p class="title">{{ user.articles_count }}</p>
                  </div>
                </div>
                <div class="level-item has-text-centered">
                  <div>
                    <p class="heading">Qiita打率</p>
                    <p class="title">{{ user.contribution_average }}</p>
                  </div>
                </div>
                <div class="level-item has-text-centered">
                  <div>
                    <p class="heading">はてブ打率</p>
                    <p class="title">{{ user.hatena_count_average }}</p>
                  </div>
                </div>
              </div>
            </div>

          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
  import {mapState, mapActions} from 'vuex'

  export default {
    name: 'Scouter',
    data() {
      return {
        userName: '',
      }
    },
    computed: {
      ...mapState('UserStore', [
        'user',
      ]),
      qiitaUrl: function () {
        return 'https://qiita.com/' + this.$data.userName;
      },
      isCardVisible: function () {
        return 0 !== Object.keys(this.user).length
      }
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
  .card {
    border-radius: 0.5em;
  }
</style>
