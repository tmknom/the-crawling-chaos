package infrastructure.qiita.article

import javax.inject.{Inject, Singleton}

import domain.qiita.article.detail.QiitaArticleBody
import domain.qiita.article.{QiitaArticleGateway, QiitaArticlePostedDateTime, QiitaItemId}
import library.scalaj.{HttpHeaders, ScalajHttpAdaptor}

@Singleton
final class HttpQiitaArticleGateway @Inject()(scalajHttpAdaptor: ScalajHttpAdaptor) extends QiitaArticleGateway {
  val BaseUrl = "https://qiita.com/items?page="

  override def fetch(qiitaItemId: QiitaItemId): (QiitaArticlePostedDateTime, QiitaArticleBody) = {
    // TODO 環境変数の設定が雑なのでなんとかしたい
    val qiitaAccessToken = sys.env("QIITA_ACCESS_TOKEN")

    val response = scalajHttpAdaptor.get(
      url     = qiitaItemId.apiUrl,
      headers = HttpHeaders(Map("Authorization" -> s"Bearer $qiitaAccessToken"))
    )
    QiitaArticleParser(response).parse
  }
}
