package domain.qiita.userranking

final case class QiitaUserRanking(id:           QiitaUserRankingId,
                                  name:         QiitaUserRankingName,
                                  contribution: QiitaUserRankingContribution)

final case class QiitaUserRankingId(value: Int)

final case class QiitaUserRankingName(value: String)

final case class QiitaUserRankingContribution(value: Int)
