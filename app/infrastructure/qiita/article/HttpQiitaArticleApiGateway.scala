package infrastructure.qiita.article

import javax.inject.{Inject, Singleton}

import domain.qiita.article.json.RawArticleJson
import domain.qiita.article.{QiitaArticleApiGateway, QiitaItemId}
import library.scalaj.{HttpHeaders, ScalajHttpAdaptor}
import play.api.Configuration

@Singleton
final class HttpQiitaArticleApiGateway @Inject()(scalajHttpAdaptor: ScalajHttpAdaptor, configuration: Configuration) extends QiitaArticleApiGateway {
  private val AccessTokenKeyName = "app.qiita.access_token"

  override def fetch(qiitaItemId: QiitaItemId): RawArticleJson = {
    val authorization: String = "Bearer " + configuration.get[String](AccessTokenKeyName)
    val headers  = HttpHeaders(Map("Authorization" -> authorization))
    val response = scalajHttpAdaptor.get(url = qiitaItemId.apiUrl, headers = headers)
    RawArticleJson(response)
  }
}
