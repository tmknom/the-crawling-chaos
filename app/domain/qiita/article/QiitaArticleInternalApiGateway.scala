package domain.qiita.article

import domain.qiita.article.json.RawArticleJson

trait QiitaArticleInternalApiGateway {
  def fetch(qiitaItemId: QiitaItemId): RawArticleJson
}
