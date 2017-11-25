package infrastructure.qiita.user

import domain.qiita._
import domain.qiita.user.{QiitaUser, QiitaUserGateway}

final class HttpQiitaUserGateway extends QiitaUserGateway {
  private val BaseUrl = "https://qiita.com/users?char="

  override def fetch(): Seq[QiitaUser] = {
    Seq(QiitaUser())
  }
}
