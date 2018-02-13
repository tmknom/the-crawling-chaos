<template>
  <div id="users" class="columns">
    <div class="column">
      <div class="card">
        <div class="card-header">
          <div class="card-header-title">
            <h2 class="subtitle">いいね！ランキング</h2>
          </div>
        </div>
        <b-table
          :data="contributions"
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
    </div>
    <div class="column">
      <div class="card">
        <div class="card-header">
          <div class="card-header-title">
            <h2 class="subtitle">投稿数ランキング</h2>
          </div>
        </div>
        <b-table
          :data="articles_counts"
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
    </div>
  </div>
</template>

<script>
  export default {
    name: 'UserList',
    props: [
      'contributions',
      'articles_counts'
    ],
    methods: {
      url(name) {
        return 'https://qiita.com/' + name;
      }
    }
  }
</script>

<style lang="scss" scoped>
  .card {
    border-radius: 0.3em;
  }

  .card-header {
    border-bottom: solid 1px #dbdbdb;
  }

  .subtitle {
    font-weight: bold;
  }

  /deep/ {
    th .th-wrap {
      font-size: 0.87em;
    }
  }
</style>
