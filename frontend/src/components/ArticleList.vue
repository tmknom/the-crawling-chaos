<template>

  <div id="articles">
    <b-table
      :data="articles"
      :bordered="false"
      :striped="true"
      :narrowed="false"
      :hoverable="true"
      :loading="false"
      :mobile-cards="true">

      <template slot-scope="props">
        <b-table-column field="index" label="順位" width="60" numeric>
          {{ props.row.index }}
        </b-table-column>

        <b-table-column field="title" label="タイトル">
          <a :href="props.row.article.url">{{ props.row.article.name }}</a>
        </b-table-column>

        <b-table-column field="likes_count" label="いいね" width="70" numeric>
          {{ props.row.contribution.likes_count.toLocaleString() }}
        </b-table-column>

        <b-table-column field="hatena_count" label="はてブ" width="70" numeric>
          {{ props.row.contribution.hatena_count.toLocaleString() }}
        </b-table-column>

        <b-table-column field="facebook_count" label="FB" width="70" numeric>
          {{ props.row.contribution.facebook_count.toLocaleString() }}
        </b-table-column>

        <b-table-column field="pocket_count" label="Pocket" width="70" numeric>
          {{ props.row.contribution.pocket_count.toLocaleString() }}
        </b-table-column>
      </template>
    </b-table>
  </div>
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
    ],
    template: '<div>{{ message }}</div>'
  }
</script>

<style lang="scss" scoped>
  /deep/ {
    th {
      padding-left: 6px;
    }

    th .th-wrap {
      font-size: 0.87em;
    }
  }
</style>
