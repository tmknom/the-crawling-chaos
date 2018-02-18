<template>
  <section class="hero is-primary is-bold">
    <!-- Hero head: will stick at the top -->
    <div class="hero-head">
      <header class="navbar">
        <div class="container">
          <div class="navbar-menu">
            <div class="navbar-end">
              <a class="navbar-item is-active">
                <router-link :to="{name: 'Index'}">Home</router-link>
              </a>
              <a class="navbar-item">
                <router-link :to="{name: 'About'}">About</router-link>
              </a>
              <span class="navbar-item">
              <a href="https://twitter.com/tmknom" class="button is-success is-inverted text-twitter">
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
    <div class="hero-body">
      <div class="container has-text-centered">
        <h1 class="title">
          Title
        </h1>
        <h2 class="subtitle">
          Subtitle
        </h2>
      </div>
    </div>

    <!-- Hero footer: will stick at the bottom -->
    <div class="hero-foot">
      <nav class="tabs is-boxed is-fullwidth">
        <div class="container">
          <ul>
            <li :class="tabClass.usersSearch">
              <router-link :to="{name: 'Scouter'}">あなたのランクは？</router-link>
            </li>
            <li :class="tabClass.userRanking">
              <router-link :to="{name: 'Users'}">ユーザランキング</router-link>
            </li>
            <li :class="tabClass.articleRanking">
              <router-link :to="{name: 'Articles'}">記事ランキング</router-link>
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
          usersSearch: '',
          userRanking: '',
          articleRanking: '',
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
            this.$data.tabClass.usersSearch = activeClassName;
            break;
          case 'Articles':
            this.$data.tabClass.articleRanking = activeClassName;
            break;
          case 'Scouter':
            this.$data.tabClass.usersSearch = activeClassName;
            break;
          case 'Users':
            this.$data.tabClass.userRanking = activeClassName;
            break;
          case 'About':
            this.$data.tabClass.about = activeClassName;
            break;
          default:
            this.$data.tabClass.usersSearch = activeClassName;
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
  .navbar-item a {
    color: rgba(255, 255, 255, 0.7);
  }

  .navbar-end {
    .text-twitter {
      color: #55acee;
    }
  }
</style>
