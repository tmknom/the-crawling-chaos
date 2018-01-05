package infrastructure.qiita.article

import javax.inject.{Inject, Singleton}

import domain.qiita.article.{QiitaArticle, QiitaArticleListGateway}
import library.scalaj.ScalajHttpAdaptor

@Singleton
final class HttpQiitaArticleListGateway @Inject()(scalajHttpAdaptor: ScalajHttpAdaptor) extends QiitaArticleListGateway {
  val BaseUrl = "https://qiita.com/items?page="

  override def fetch(pageNumber: Int): List[QiitaArticle] = {
    val response = scalajHttpAdaptor.get(BaseUrl + pageNumber.toString)
    QiitaArticleListParser(response).parse
  }
}
