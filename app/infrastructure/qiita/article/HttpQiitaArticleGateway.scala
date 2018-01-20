package infrastructure.qiita.article

import javax.inject.{Inject, Singleton}

import domain.qiita.article.json.RawPropsArticleJson
import domain.qiita.article.{QiitaArticle, QiitaArticleGateway, QiitaItemId}
import library.scalaj.ScalajHttpAdaptor

@Singleton
final class HttpQiitaArticleGateway @Inject()(scalajHttpAdaptor: ScalajHttpAdaptor) extends QiitaArticleGateway {
  override def fetch(qiitaItemId: QiitaItemId): (QiitaArticle, RawPropsArticleJson) = {
    val response = scalajHttpAdaptor.get(qiitaItemId.url)
    QiitaArticleParser(response).parse
  }
}
