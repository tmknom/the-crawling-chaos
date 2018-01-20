package infrastructure.qiita.article

import javax.inject.{Inject, Singleton}

import domain.qiita.article.json.RawPropsArticleJson
import domain.qiita.article.{QiitaArticleInternalApiGateway, QiitaItemId}
import library.scalaj.ScalajHttpAdaptor

@Singleton
final class HttpQiitaArticleInternalApiGateway @Inject()(scalajHttpAdaptor: ScalajHttpAdaptor) extends QiitaArticleInternalApiGateway {
  override def fetch(qiitaItemId: QiitaItemId): RawPropsArticleJson = {
    val response = scalajHttpAdaptor.get(qiitaItemId.url)
    QiitaArticleInternalApiParser(response).parse
  }
}
