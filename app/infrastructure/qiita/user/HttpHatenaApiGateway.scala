package infrastructure.qiita.user

import javax.inject.{Inject, Singleton}

import domain.qiita.article.contribution.HatenaCount
import domain.qiita.user.QiitaUserName
import domain.qiita.user.contribution.HatenaApiGateway
import library.scalaj.ScalajHttpAdaptor

@Singleton
final class HttpHatenaApiGateway @Inject()(scalajHttpAdaptor: ScalajHttpAdaptor) extends HatenaApiGateway {
  override def fetch(qiitaUserName: QiitaUserName): HatenaCount = {
    val headers = scalajHttpAdaptor.getHeaders(qiitaUserName.urlHatena)
    HatenaParser(headers).parse
  }
}
