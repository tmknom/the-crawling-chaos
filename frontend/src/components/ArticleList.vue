<template>
  <ul id="articles">
    <ArticleItem v-for="item in articles" :item="item" :key="item.article.item_id"></ArticleItem>
  </ul>
</template>

<script>
  import ArticleItem from './ArticleItem';

  export default {
    name: 'ArticleList',
    components: {
      ArticleItem
    },
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
</style>
