<template>

  <el-table
    :data="articles"
    stripe
    style="width: 100%">
    <el-table-column
      class-name="left"
      prop="index"
      label="順位"
      width="60">
    </el-table-column>
    <el-table-column
      class-name="left cell-link"
      label="タイトル"
      width="520">
      <template slot-scope="scope">
        <a v-bind:href="scope.row.article.url">{{ scope.row.article.name }}</a>
      </template>
    </el-table-column>
    <el-table-column
      class-name="right"
      prop="contribution.likes_count"
      label="いいね">
    </el-table-column>
    <el-table-column
      class-name="right"
      prop="contribution.hatena_count"
      label="はてブ">
    </el-table-column>
    <el-table-column
      class-name="right"
      label-class-name="label-small"
      prop="contribution.facebook_count"
      label="Facebook">
    </el-table-column>
    <el-table-column
      class-name="right"
      prop="contribution.pocket_count"
      label="Pocket">
    </el-table-column>
  </el-table>
  <!--<ul id="articles">-->
  <!--<ArticleItem v-for="item in articles" :item="item" :key="item.article.item_id"></ArticleItem>-->
  <!--</ul>-->
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
    .cell-link {
      a {
        color: #42b983;
      }
    }

    .label-small {
      font-size: 0.97em;
    }

    .left {
      text-align: left;
    }

    .right {
      text-align: right;
    }
  }
</style>
