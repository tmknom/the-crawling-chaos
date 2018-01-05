package domain.qiita.article

final case class QiitaArticle(
    id:     QiitaArticleId,
    itemId: QiitaItemId,
    title:  QiitaArticleTitle,
    url:    QiitaArticleUrl
) {}
