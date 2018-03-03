<template>
  <div class="box" v-show="isCardVisible">
    <div v-if="isNouhu">
      <h2>Qiita戦闘力…たったの {{ total }} か…ゴミめ…</h2>
    </div>
    <div v-else>
      <h2 class="subtitle is-3">Qiita戦闘力は<span class="db-character">{{ dbCharacter }}</span>レベルです！</h2>
      <h3 class="subtitle is-4">あなたは上位 <span class="rare">{{ rare }}%</span> に食い込んでいます！！ </h3>
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
    }
  }
</script>

<style lang="scss" scoped>
  .db-character {
    color: #ff0099;
    font-size: 1.5em;
  }

  .rare {
    color: #000099;
    font-size: 1.2em;
  }
</style>
