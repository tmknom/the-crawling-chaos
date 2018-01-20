package domain.qiita.article

import domain.qiita.article.json.RawPropsArticleJson

trait QiitaArticleGateway {
  def fetch(qiitaItemId: QiitaItemId): (QiitaArticle, RawPropsArticleJson)
}
