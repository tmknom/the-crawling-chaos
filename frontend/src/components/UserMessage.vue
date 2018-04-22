<template>
  <div class="box" v-show="isCardVisible">
    <div v-if="isNouhu">
      <h2>Qiita戦闘力…たったの {{ total }} か…ゴミめ…</h2>
    </div>
    <div v-else>
      <h2 class="subtitle is-3">Qiita戦闘力は<span class="is-size-1 has-text-danger">{{ dbCharacter }}</span>レベルです！</h2>
      <h3 class="subtitle is-4">あなたは上位 <span class="is-size-2 has-text-info">{{ rare }}%</span> に食い込んでいます！！ </h3>
    </div>
    <div class="field is-grouped is-grouped-multiline share-button">
      <div class="control button is-large bd-tw-button">
        <a :href="tweetUrl('Qiita戦闘力' + total + 'で、全国ランキング' + rank + '位でした！')"
           target="_blank"
           rel="noopener noreferrer">

          <span class="icon"><i class="fab fa-twitter"></i></span>
          <span class="has-text-weight-bold">結果をツイートする</span>
        </a>
      </div>

      <div class="control button is-large bd-fa-button">
        <a href="https://facebook.com/sharer/sharer.php?u=https://engineer-scouter.firebaseapp.com/"
           target="_blank"
           rel="noopener noreferrer">

          <span class="icon"><i class="fab fa-facebook"></i></span>
          <span class="has-text-weight-bold">結果をシェアする</span>
        </a>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'UserMessage',
    props: [
      'user'
    ],
    data() {
      return {
        totalUserCount: 35000,
      }
    },
    computed: {
      isCardVisible: function () {
        return 0 !== Object.keys(this.user).length
      },
      rank: function () {
        return this.localeNumber(this.user.rank);
      },
      total: function () {
        return this.localeNumber(this.user.total);
      },
      isNouhu: function () {
        return this.user.total <= 5;
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
      }
    },
    methods: {
      localeNumber(number) {
        return Number(number).toLocaleString();
      },
      tweetUrl(message) {
        const baseUrl = 'http://twitter.com/share';
        const encodedMessage = encodeURIComponent(message);
        const url = 'https://engineer-scouter.firebaseapp.com/';
        const hashtags = 'EngineerScouter';
        const via = 'tmknom';
        return baseUrl + '?text=' + encodedMessage + '&url=' + url + '&hashtags=' + hashtags + '&via=' + via;
      }
    }
  }
</script>

<style lang="scss" scoped>
  .share-button {
    margin-top: 50px;
    border-color: transparent !important;
    a {
      color: white;
    }
  }

  .bd-tw-button {
    background-color: #55acee;
  }

  .bd-fa-button {
    background-color: #3b5998;
  }
</style>
