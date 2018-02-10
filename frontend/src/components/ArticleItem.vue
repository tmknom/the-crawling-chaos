<template>
  <li class="article">
    {{ article.index }}：<a v-bind:href="article.article.url">{{ article.article.name }}</a>
    {{ article.contribution.likes_count }}
  </li>
</template>

<script>
  export default {
    name: 'ArticleItem',
    props: [
      'article'
    ]
  }
</script>

<style lang="scss" scoped>
  .article {
    margin: 0 10px;
    text-align: left;
  }

  .article a {
    color: #42b983;
  }
</style>
