package infrastructure.qiita.user

import javax.inject.{Inject, Singleton}

import domain.qiita.user.{QiitaUser, QiitaUserGateway}
import library.scalaj.ScalajHttpAdaptor

@Singleton
final class HttpQiitaUserGateway @Inject()(scalajHttpAdaptor: ScalajHttpAdaptor) extends QiitaUserGateway {
  override def fetch(url: String): Seq[QiitaUser] = {
    val response = scalajHttpAdaptor.get(url)
    QiitaUserParser(response).parse
  }
}
