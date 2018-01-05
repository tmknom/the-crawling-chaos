package infrastructure.qiita.article

import javax.inject.{Inject, Singleton}

import domain.qiita.article.{QiitaArticle, QiitaArticleIdGateway}
import library.scalaj.ScalajHttpAdaptor

@Singleton
final class HttpQiitaArticleIdGateway @Inject()(scalajHttpAdaptor: ScalajHttpAdaptor) extends QiitaArticleIdGateway {
  val BaseUrl = "https://qiita.com/items?page="

  override def fetch(pageNumber: Int): List[QiitaArticle] = {
    val response = scalajHttpAdaptor.get(BaseUrl + pageNumber.toString)
    QiitaArticleIdParser(response).parse
  }
}
