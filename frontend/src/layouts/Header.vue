<template>
  <section class="hero is-primary is-bold">
    <!-- Hero head: will stick at the top -->
    <div class="hero-head">
      <header class="navbar">
        <div class="container">
          <div class="navbar-menu">
            <div class="navbar-end">
              <a class="navbar-item">
                <router-link :to="{name: 'Index'}">Home</router-link>
              </a>
              <span class="navbar-item">
              <a href="https://twitter.com/tmknom"
                 class="button is-success is-inverted text-twitter"
                 target="_blank"
                 rel="noopener noreferrer">
                <span class="icon">
                  <i class="fab fa-twitter"></i>
                </span>
                <span>Twitter</span>
              </a>
            </span>
            </div>
          </div>
        </div>
      </header>
    </div>

    <!-- Hero content: will be in the middle -->
    <router-link :to="{name: 'Index'}">
      <div class="hero-body">
        <div class="container has-text-centered">
          <h1 class="title">
            エンジニアスカウター
          </h1>
        </div>
      </div>
    </router-link>

    <!-- Hero footer: will stick at the bottom -->
    <div class="hero-foot">
      <nav class="tabs is-boxed is-fullwidth">
        <div class="container">
          <ul>
            <li :class="tabClass.scouter">
              <router-link :to="{name: 'Scouter'}">Qiita戦闘力計測</router-link>
            </li>
            <li :class="tabClass.userTotalRanking">
              <router-link :to="{name: 'UserTotal'}">戦闘力ランキング</router-link>
            </li>
            <li :class="tabClass.userContributionRanking">
              <router-link :to="{name: 'UserContribution'}">Contributionランキング</router-link>
            </li>
            <li :class="tabClass.userHatenaRanking">
              <router-link :to="{name: 'UserHatena'}">はてブランキング</router-link>
            </li>
            <li :class="tabClass.about">
              <router-link :to="{name: 'About'}">このサイトについて</router-link>
            </li>
          </ul>
        </div>
      </nav>
    </div>
  </section>
</template>

<script>
  export default {
    name: 'Header',
    data() {
      return {
        tabClass: {
          scouter: '',
          userTotalRanking: '',
          userContributionRanking: '',
          userHatenaRanking: '',
          about: '',
        }
      }
    },
    methods: {
      renderTab() {
        this.clearTab();
        this.changeTab();
      },
      changeTab() {
        const activeClassName = 'is-active';
        switch (this.$route.name) {
          case 'Index':
            this.$data.tabClass.scouter = activeClassName;
            break;
          case 'UserContribution':
            this.$data.tabClass.userContributionRanking = activeClassName;
            break;
          case 'Scouter':
            this.$data.tabClass.scouter = activeClassName;
            break;
          case 'UserTotal':
            this.$data.tabClass.userTotalRanking = activeClassName;
            break;
          case 'UserHatena':
            this.$data.tabClass.userHatenaRanking = activeClassName;
            break;
          case 'About':
            this.$data.tabClass.about = activeClassName;
            break;
          default:
            this.$data.tabClass.scouter = activeClassName;
        }
      },
      clearTab() {
        Object.keys(this.$data.tabClass).forEach(key => {
          this.$data.tabClass[key] = '';
        });
      },
    },
    watch: {
      '$route'(newValue, oldValue) {
        this.renderTab();
      }
    },
    created() {
      this.renderTab();
    }
  }
</script>

<style lang="scss" scoped>
  .title a {
    color: white;
  }

  .navbar-item a {
    color: rgba(255, 255, 255, 0.7);
  }

  .navbar-end {
    .text-twitter {
      color: #55acee;
    }
  }
</style>
