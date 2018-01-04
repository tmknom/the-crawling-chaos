package infrastructure.qiita.user.contribution

import javax.inject.{Inject, Singleton}

import domain.qiita.user.QiitaUser
import domain.qiita.user.contribution.QiitaUserContributionGateway
import domain.qiita.user.summary.QiitaUserSummary
import library.scalaj.ScalajHttpAdaptor

@Singleton
final class HttpQiitaUserContributionGateway @Inject()(scalajHttpAdaptor: ScalajHttpAdaptor) extends QiitaUserContributionGateway {

  def fetch(qiitaUser: QiitaUser): QiitaUserSummary = {
    val response                               = scalajHttpAdaptor.get(qiitaUser.name.urlHovercardUsers)
    val (qiitaUserContribution, articlesCount) = QiitaUserContributionParser(response).parse

    QiitaUserSummary(
      id            = qiitaUser.id,
      name          = qiitaUser.name,
      contribution  = qiitaUserContribution,
      articlesCount = articlesCount
    )
  }
}
