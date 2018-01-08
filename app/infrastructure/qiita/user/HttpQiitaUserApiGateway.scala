package infrastructure.qiita.user

import javax.inject.{Inject, Singleton}

import domain.qiita.user.{QiitaUserApiGateway, QiitaUserName}
import library.scalaj.ScalajHttpAdaptor

@Singleton
final class HttpQiitaUserApiGateway @Inject()(scalajHttpAdaptor: ScalajHttpAdaptor) extends QiitaUserApiGateway {
  val BaseUrl = "https://qiita.com/api/v2/users?per_page=100&page="

  override def fetch(page: Int): Seq[QiitaUserName] = {
    val response = scalajHttpAdaptor.get(BaseUrl + page.toString)
    QiitaUserApiParser(response).parse
  }
}
