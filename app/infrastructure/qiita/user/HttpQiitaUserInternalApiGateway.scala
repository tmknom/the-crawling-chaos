package infrastructure.qiita.user

import javax.inject.{Inject, Singleton}

import domain.qiita.user.{QiitaUserInternalApiGateway, QiitaUserName, RawInternalUserJson}
import library.scalaj.ScalajHttpAdaptor

@Singleton
final class HttpQiitaUserInternalApiGateway @Inject()(scalajHttpAdaptor: ScalajHttpAdaptor) extends QiitaUserInternalApiGateway {
  def fetch(qiitaUserName: QiitaUserName): RawInternalUserJson = {
    val response = scalajHttpAdaptor.get(qiitaUserName.urlHovercardUsers)
    RawInternalUserJson(response)
  }
}
