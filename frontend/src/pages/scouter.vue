<template>
  <section class="section">
    <h1 class="title">ユーザ検索</h1>
    <div class="columns">
      <div class="column is-8">
        <b-input v-model="userName"
                 @keyup.native.enter="handle"
                 placeholder="ユーザ名を入力してください"
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
          <span>Push</span>
        </a>
      </div>
    </div>

    <div class="columns">
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
              <table class="table">
                <tr>
                  <th>総合評価</th>
                  <th>{{ total }}</th>
                </tr>
                <tr>
                  <th>いいね数</th>
                  <th>{{ contribution }}</th>
                </tr>
                <tr>
                  <th>はてブ数</th>
                  <th>{{ hatena_count }}</th>
                </tr>
                <tr>
                  <th>平均いいね数</th>
                  <th>{{ contribution_average }}</th>
                </tr>
                <tr>
                  <th>平均はてブ数</th>
                  <th>{{ hatena_count_average }}</th>
                </tr>
                <tr>
                  <th>投稿数</th>
                  <th>{{ articles_count }}</th>
                </tr>
              </table>
            </div>
          </div>
        </div>
      </div>

      <div class="column is-8">
        <div class="box" v-show="isCardVisible">
          <div v-if="isNouhu">
            <h2>Qiita戦闘力…たったの {{ total }} か…ゴミめ…</h2>
          </div>
          <div v-else>
            <h2 class="subtitle is-3">Qiita戦闘力は<span class="db-character">{{ dbCharacter }}</span>レベルです！</h2>
            <h3 class="subtitle is-4">あなたは上位 <span class="rare">{{ rare }}%</span> に食い込んでいます！！ </h3>
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
        totalUserCount: 35000,
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
      },
      isNouhu: function () {
        return this.user.total <= 5;
      },
      rank: function () {
        return this.localeNumber(this.user.rank);
      },
      total: function () {
        return this.localeNumber(this.user.total);
      },
      contribution: function () {
        return this.localeNumber(this.user.contribution);
      },
      hatena_count: function () {
        return this.localeNumber(this.user.hatena_count);
      },
      contribution_average: function () {
        return this.localeNumber(this.user.contribution_average);
      },
      hatena_count_average: function () {
        return this.localeNumber(this.user.hatena_count_average);
      },
      articles_count: function () {
        return this.localeNumber(this.user.articles_count);
      },
      rare: function () {
        return (100 * (this.user.rank / this.totalUserCount)).toFixed(2);
      },
      dbCharacter: function () {
        const power = this.user.total;
        if (power <= 5) {
          return 'ゴミ';
        } else if (power <= 100) {
          return '亀仙人';
        } else if (power <= 130) {
          return '桃白白';
        } else if (power <= 150) {
          return 'シンバル';
        } else if (power <= 170) {
          return 'タンバリン';
        } else if (power <= 210) {
          return 'ドラム';
        } else if (power <= 230) {
          return 'ピッコロ大魔王';
        } else if (power <= 900) {
          return 'ヤジロベー';
        } else if (power <= 1200) {
          return 'サイバイマン';
        } else if (power <= 1500) {
          return 'ラディッツ';
        } else if (power <= 4000) {
          return 'ナッパ';
        } else if (power <= 10000) {
          return 'グルド';
        } else if (power <= 18000) {
          return 'キュイ';
        } else if (power <= 20000) {
          return 'バビディ';
        } else if (power <= 22000) {
          return 'ドドリア';
        } else if (power <= 26000) {
          return 'ザーボン';
        } else if (power <= 50000) {
          return 'リクーム';
        } else if (power <= 120000) {
          return 'ギニュー';
        } else if (power <= 530000) {
          return 'フリーザ';
        } else {
          return '魔人ブウ';
        }
      },
    },
    methods: {
      ...mapActions('UserStore', [
        'fetchUser',
      ]),
      localeNumber(number) {
        return Number(number).toLocaleString();
      },
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

  .db-character {
    color: #ff0099;
    font-size: 1.5em;
  }

  .rare {
    color: #000099;
    font-size: 1.2em;
  }
</style>
