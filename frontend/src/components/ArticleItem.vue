<template>
  <li class="article">
    {{ item.index }}：<a v-bind:href="item.article.url">{{ item.article.name }}</a>
    {{ item.contribution.likes_count }}, {{ item.contribution.comments_count }}, {{ item.contribution.hatena_count }},
    {{ item.contribution.facebook_count }}, {{ item.contribution.pocket_count }}
  </li>
</template>

<script>
  export default {
    name: 'ArticleItem',
    props: [
      'item'
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
