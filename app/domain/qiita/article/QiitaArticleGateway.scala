package domain.qiita.article

import domain.qiita.article.json.RawArticleJson

trait QiitaArticleGateway {
  def fetch(qiitaItemId: QiitaItemId): (QiitaArticle, RawArticleJson)
}
