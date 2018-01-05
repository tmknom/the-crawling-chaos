package infrastructure.qiita.article

import javax.inject.{Inject, Singleton}

import domain.qiita.article.{QiitaArticleIdGateway, QiitaItemId}
import library.scalaj.ScalajHttpAdaptor

@Singleton
final class HttpQiitaArticleIdGateway @Inject()(scalajHttpAdaptor: ScalajHttpAdaptor) extends QiitaArticleIdGateway {
  val BaseUrl = "https://qiita.com/items?page="

  override def fetch(pageNumber: Int): List[QiitaItemId] = {
    val response = scalajHttpAdaptor.get(BaseUrl + pageNumber.toString)
    QiitaArticleIdParser(response).parse
  }
}
