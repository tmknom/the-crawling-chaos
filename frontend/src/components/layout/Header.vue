<template>
  <section class="hero is-primary is-bold">
    <!-- Hero head: will stick at the top -->
    <div class="hero-head">
      <header class="navbar">
        <div class="container">
          <div class="navbar-brand">
            <a class="navbar-item">
              <img src="https://bulma.io/images/bulma-type-white.png" alt="Logo">
            </a>
            <span class="navbar-burger burger" data-target="navbarMenuHeroC">
            <span></span>
            <span></span>
            <span></span>
          </span>
          </div>
          <div id="navbarMenuHeroC" class="navbar-menu">
            <div class="navbar-end">
              <a class="navbar-item is-active">
                <router-link to="/">Home</router-link>
              </a>
              <a class="navbar-item">
                <router-link to="/about">About</router-link>
              </a>
              <a class="navbar-item">
                Documentation
              </a>
              <span class="navbar-item">
              <a class="button is-success is-inverted">
                <span class="icon">
                  <i class="fab fa-github"></i>
                </span>
                <span>Download</span>
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
            <li :class="tabClass.usersSearch" @click="handle">
              <router-link to="/users/search">あなたのランクは？</router-link>
            </li>
            <li :class="tabClass.userRanking" @click="handle">
              <router-link to="/ranking/users">ユーザランキング</router-link>
            </li>
            <li :class="tabClass.articleRanking" @click="handle">
              <router-link to="/">記事ランキング</router-link>
            </li>
            <li :class="tabClass.about" @click="handle">
              <router-link to="/about">このサイトについて</router-link>
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
      handle() {
        this.renderTab();
      },
      renderTab() {
        this.clearTab();
        this.changeTab();
      },
      changeTab() {
        const activeClassName = 'is-active';
        switch (this.$route.path) {
          case '/':
            this.$data.tabClass.articleRanking = activeClassName;
            break;
          case '/users/search':
            this.$data.tabClass.usersSearch = activeClassName;
            break;
          case '/ranking/users':
            this.$data.tabClass.userRanking = activeClassName;
            break;
          case '/about':
            this.$data.tabClass.about = activeClassName;
            break;
          default:
            this.$data.tabClass.articleRanking = activeClassName;
        }
      },
      clearTab() {
        Object.keys(this.$data.tabClass).forEach(key => {
          this.$data.tabClass[key] = '';
        });
      },
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
</style>
