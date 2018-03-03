package domain.qiita.user

import scalikejdbc.{AutoSession, DBSession}

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments"))
trait QiitaUserRepository {
  def retrieveTop100()(implicit session: DBSession = AutoSession): List[QiitaUser]

  def countTotalEvaluation()(implicit session: DBSession = AutoSession): Long

  def retrieveTotalEvaluation(limit: Int, offset: Int)(implicit session: DBSession = AutoSession): List[QiitaUser]

  def countContribution()(implicit session: DBSession = AutoSession): Long

  def retrieveContribution(limit: Int, offset: Int)(implicit session: DBSession = AutoSession): List[QiitaUser]

  def retrieveContributionAverage(limit: Int, offset: Int)(implicit session: DBSession = AutoSession): List[QiitaUser]

  def countHatenaCount()(implicit session: DBSession = AutoSession): Long

  def retrieveHatenaCount(limit: Int, offset: Int)(implicit session: DBSession = AutoSession): List[QiitaUser]

  def retrieveHatenaAverage(limit: Int, offset: Int)(implicit session: DBSession = AutoSession): List[QiitaUser]

  def countArticlesCount()(implicit session: DBSession = AutoSession): Long

  def retrieveArticlesCount(limit: Int, offset: Int)(implicit session: DBSession = AutoSession): List[QiitaUser]
}
