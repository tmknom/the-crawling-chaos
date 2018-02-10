<template>
  <ul id="articles">
    <li class="article" v-for="result in articles">
      {{ result.index }}：<a v-bind:href="result.article.url">{{ result.article.name }}</a>
      {{ result.contribution.likes_count }}
    </li>
  </ul>
</template>

<script>
  export default {
    name: 'ArticleList',
    props: [
      'articles'
    ]
  }
</script>

<style lang="scss" scoped>
  #articles {
    list-style-type: none;
    padding: 0;
  }

  .article {
    margin: 0 10px;
    text-align: left;
  }

  .article a {
    color: #42b983;
  }
</style>
