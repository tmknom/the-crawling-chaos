<template>
  <section id="contents" class="section">
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
                <p class="title"><a :href="qiitaUrl">@{{ user.name }}</a></p>
              </div>
            </div>

            <div class="content">
              <div class="level">
                <div class="level-item has-text-centered">
                  <div>
                    <p class="heading">順位</p>
                    <p class="title">{{ user.index }}</p>
                  </div>
                </div>
                <div class="level-item has-text-centered">
                  <div>
                    <p class="heading">Contributions</p>
                    <p class="title">{{ user.contribution }}</p>
                  </div>
                </div>
                <div class="level-item has-text-centered">
                  <div>
                    <p class="heading">Items</p>
                    <p class="title">{{ user.articles_count }}</p>
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
  import {mapState, mapActions} from 'vuex';
  //import UserList from '../../components/UserList';

  export default {
    name: 'Scouter',
    components: {
      //UserList
    },
    data() {
      return {
        userName: '',
      }
    },
    computed: {
      ...mapState('userProfile', [
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
      ...mapActions('userProfile', [
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
