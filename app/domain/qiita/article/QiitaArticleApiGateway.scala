package domain.qiita.article

import domain.qiita.article.json.RawArticleJson

trait QiitaArticleApiGateway {
  def fetch(qiitaItemId: QiitaItemId): RawArticleJson
}
