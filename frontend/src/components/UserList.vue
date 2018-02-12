<template>
  <div id="users" class="columns">
    <div class="column">
      <h2 class="subtitle">いいね！ランキング</h2>

      <b-table
        :data="users"
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

          <b-table-column field="title" label="ユーザ名">
            <a :href="url(props.row.name)" target="_blank" rel="noopener noreferrer">
              {{ props.row.name }}
            </a>
          </b-table-column>

          <b-table-column field="likes_count" label="いいね" width="70" numeric>
            {{ props.row.contribution.toLocaleString() }}
          </b-table-column>

          <b-table-column field="hatena_count" label="記事数" width="70" numeric>
            {{ props.row.articles_count.toLocaleString() }}
          </b-table-column>
        </template>
      </b-table>
    </div>
    <div class="column">
      Second column
    </div>
  </div>
</template>

<script>
  export default {
    name: 'UserList',
    props: [
      'users'
    ],
    methods: {
      url(name) {
        return 'https://qiita.com/' + name;
      }
    }
  }
</script>

<style lang="scss" scoped>
  /deep/ {
    th .th-wrap {
      font-size: 0.87em;
    }
  }
</style>
