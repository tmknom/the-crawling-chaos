package infrastructure.qiita.user

import javax.inject.{Inject, Singleton}

import domain.qiita.user.{QiitaUserName, QiitaUserNameGateway}
import library.scalaj.ScalajHttpAdaptor

@Singleton
final class HttpQiitaUserNameGateway @Inject()(scalajHttpAdaptor: ScalajHttpAdaptor) extends QiitaUserNameGateway {
  override def fetch(url: String): Seq[QiitaUserName] = {
    val response = scalajHttpAdaptor.get(url)
    QiitaUserNameParser(response).parse
  }
}
