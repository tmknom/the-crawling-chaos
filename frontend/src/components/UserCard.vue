<template>
  <div class="card" v-show="isCardVisible">
    <div class="card-content">
      <div class="media">
        <div class="media-left">
          <figure class="image is-48x48">
            <a :href="url" target="_blank" rel="noopener noreferrer">
              <img :src="user.profile_image_url" alt="Profile image">
            </a>
          </figure>
        </div>
        <div class="media-content">
          <p class="title">全国 {{ rank }} 位</p>
          <p class="subtitle is-6"><a :href="url" target="_blank" rel="noopener noreferrer">@{{ user.name }}</a></p>
        </div>
      </div>

      <div class="content">
        <table class="table">
          <tr>
            <th>Qiita戦闘力</th>
            <th>{{ total }}</th>
          </tr>
          <tr>
            <th>Contribution数</th>
            <th>{{ contribution }}</th>
          </tr>
          <tr>
            <th>はてブ数</th>
            <th>{{ hatena_count }}</th>
          </tr>
          <tr>
            <th>平均Contribution数</th>
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
</template>

<script>
  export default {
    name: 'UserCard',
    props: [
      'user'
    ],
    computed: {
      url: function () {
        return 'https://qiita.com/' + this.user.name;
      },
      isCardVisible: function () {
        return 0 !== Object.keys(this.user).length
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
    },
    methods: {
      localeNumber(number) {
        return Number(number).toLocaleString();
      }
    }
  }
</script>

<style lang="scss" scoped>
  .card {
    border-radius: 0.5em;
  }
</style>
