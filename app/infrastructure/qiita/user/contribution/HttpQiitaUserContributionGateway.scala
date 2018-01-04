package infrastructure.qiita.user.contribution

import javax.inject.{Inject, Singleton}

import domain.qiita.user.QiitaUser
import domain.qiita.user.contribution.{QiitaUserContribution, QiitaUserContributionGateway}
import library.scalaj.ScalajHttpAdaptor

@Singleton
final class HttpQiitaUserContributionGateway @Inject()(scalajHttpAdaptor: ScalajHttpAdaptor) extends QiitaUserContributionGateway {

  def fetch(qiitaUser: QiitaUser): QiitaUserContribution = {
    val response = scalajHttpAdaptor.get(qiitaUser.name.urlHovercardUsers)
    QiitaUserContributionParser(response).parse
  }
}
