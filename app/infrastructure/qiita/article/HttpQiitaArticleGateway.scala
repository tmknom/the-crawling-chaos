package infrastructure.qiita.article

import javax.inject.{Inject, Singleton}

import domain.qiita.article.{QiitaArticle, QiitaArticleGateway}
import library.scalaj.ScalajHttpAdaptor

@Singleton
final class HttpQiitaArticleGateway @Inject()(scalajHttpAdaptor: ScalajHttpAdaptor) extends QiitaArticleGateway {
  val BaseUrl = "https://qiita.com/items?page="

  override def fetch(pageNumber: Int): List[QiitaArticle] = {
    val response = scalajHttpAdaptor.get(BaseUrl + pageNumber.toString)
    QiitaArticleParser(response).parse
  }
}
