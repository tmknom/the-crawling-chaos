package infrastructure.qiita.article

import javax.inject.{Inject, Singleton}

import domain.qiita.article.json.RawArticleJson
import domain.qiita.article.{QiitaArticleApiGateway, QiitaItemId}
import library.scalaj.ScalajHttpAdaptor

@Singleton
final class HttpQiitaArticleApiGateway @Inject()(scalajHttpAdaptor: ScalajHttpAdaptor) extends QiitaArticleApiGateway {
  override def fetch(qiitaItemId: QiitaItemId): RawArticleJson = {
    val response = scalajHttpAdaptor.get(qiitaItemId.apiUrl)
    RawArticleJson(response)
  }
}
