package domain.qiita.user

import domain.qiita.user.contribution.{ArticlesCount, Contribution}

final case class QiitaUser(profile: QiitaUserProfile, contribution: Contribution, articlesCount: ArticlesCount)
