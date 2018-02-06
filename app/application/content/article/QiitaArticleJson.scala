package application.content.article

import domain.qiita.article.QiitaArticleAggregate

object QiitaArticleJson {
  def build(aggregate: QiitaArticleAggregate, rank: Int): Map[String, _] = {
    Map(
      "index" -> rank,
      "article" -> Map(
        "item_id" -> aggregate.article.itemId.value,
        "name" -> aggregate.article.title.value,
        "url" -> aggregate.article.url.value,
        "posted" -> aggregate.article.postedDateTime.value,
        "user" -> aggregate.article.postedUserName.value
      ),
      "contribution" -> Map(
        "likes_count" -> aggregate.contribution.likesCount.value,
        "comments_count" -> aggregate.contribution.commentsCount.value,
        "hatena_count" -> aggregate.contribution.hatenaCount.value,
        "facebook_count" -> aggregate.contribution.facebookCount.value,
        "pocket_count" -> aggregate.contribution.pocketCount.value
      )
    )
  }
}
