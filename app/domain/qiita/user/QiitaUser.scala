package domain.qiita.user

import domain.qiita.user.contribution.{ArticlesCount, QiitaUserContribution}

final case class QiitaUser(profile: QiitaUserProfile, contribution: QiitaUserContribution, articlesCount: ArticlesCount)
