package application.qiita.user

import javax.inject.{Inject, Singleton}

import domain.qiita.user.summary.{QiitaUserSummary, QiitaUserSummaryRepository}

@Singleton
class QiitaUserContributionApplication @Inject()(
    repository: QiitaUserSummaryRepository
) {
  def list(): List[QiitaUserSummary] = {
    repository.retrieveTop200()
  }
}
