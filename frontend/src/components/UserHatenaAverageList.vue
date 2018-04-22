<template>
  <b-table
    :data="items"
    :bordered="false"
    :striped="true"
    :narrowed="false"
    :hoverable="true"
    :loading="false"
    :mobile-cards="true">

    <template slot-scope="props">
      <b-table-column field="rank" label="順位" width="60" numeric>
        {{ props.row.rank.toLocaleString() }}
      </b-table-column>

      <b-table-column field="title" label="ユーザ名">
        <a :href="url(props.row.name)" target="_blank" rel="noopener noreferrer">
          {{ props.row.name }}
        </a>
      </b-table-column>

      <b-table-column field="hatena_count" label="打点" width="75" numeric>
        {{ props.row.hatena_count.toLocaleString() }}
      </b-table-column>

      <b-table-column field="articles_count" label="打席数" width="75" numeric>
        {{ props.row.articles_count.toLocaleString() }}
      </b-table-column>

      <b-table-column field="hatena_count_average" label="平均" width="75" numeric>
        {{ props.row.hatena_count_average.toLocaleString() }}
      </b-table-column>
    </template>
  </b-table>
</template>

<script>
  export default {
    name: 'UserHatenaAverageList',
    props: [
      'items'
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
