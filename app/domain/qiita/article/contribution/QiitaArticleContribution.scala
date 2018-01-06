package domain.qiita.article.contribution

/**
  * Qiita 記事の評価
  */
final case class QiitaArticleContribution(
    likesCount:    LikesCount,
    commentsCount: CommentsCount
)
