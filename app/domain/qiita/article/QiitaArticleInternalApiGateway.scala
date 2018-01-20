package domain.qiita.article

import domain.qiita.article.json.RawPropsArticleJson

trait QiitaArticleInternalApiGateway {
  def fetch(qiitaItemId: QiitaItemId): RawPropsArticleJson
}
