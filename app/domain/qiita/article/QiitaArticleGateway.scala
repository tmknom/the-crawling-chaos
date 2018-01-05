package domain.qiita.article

import domain.qiita.article.detail.QiitaArticleBody

trait QiitaArticleGateway {
  def fetch(qiitaItemId: QiitaItemId): (QiitaArticlePostedDateTime, QiitaArticleBody)
}
