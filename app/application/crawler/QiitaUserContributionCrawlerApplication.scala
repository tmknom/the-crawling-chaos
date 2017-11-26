package application.crawler

import javax.inject.{Inject, Singleton}

import domain.qiita.user.QiitaUserRepository
import domain.qiita.user.contribution.{QiitaUserContributionGateway, QiitaUserContributionRepository}
import domain.qiita.userranking.QiitaUserRankingRepository

@Singleton
final class QiitaUserContributionCrawlerApplication @Inject()(
    gateway:                    QiitaUserContributionGateway,
    repository:                 QiitaUserContributionRepository,
    qiitaUserRankingRepository: QiitaUserRankingRepository,
    qiitaUserRepository:        QiitaUserRepository
) {

  def crawl(): Unit = {}

}
