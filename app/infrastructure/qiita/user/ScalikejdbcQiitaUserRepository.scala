package infrastructure.qiita.user

import javax.inject.Singleton

import domain.qiita.article.contribution.HatenaCount
import domain.qiita.user._
import domain.qiita.user.contribution.{ArticlesCount, Contribution, QiitaUserContribution}
import scalikejdbc._

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments", "org.wartremover.warts.Nothing"))
@Singleton
final class ScalikejdbcQiitaUserRepository extends QiitaUserRepository {

  override def retrieveTop100()(implicit session: DBSession = AutoSession): List[QiitaUser] = {
    sql"""
          SELECT * FROM qiita_users AS qu
          INNER JOIN qiita_user_contributions AS quc
          ON qu.user_name = quc.user_name
          ORDER BY quc.contribution DESC
          LIMIT 100;
       """
      .map(toQiitaUser)
      .list()
      .apply()
  }

  override def countContribution()(implicit session: DBSession = AutoSession): Long = {
    sql"""
          SELECT COUNT(user_name) FROM qiita_user_contributions
          WHERE contribution > 0;
       """
      .map(_.long(1))
      .single()
      .apply()
      .get
  }

  override def retrieveContribution(limit: Int, offset: Int)(implicit session: DBSession = AutoSession): List[QiitaUser] = {
    sql"""
          SELECT * FROM qiita_users AS qu
          INNER JOIN qiita_user_contributions AS quc
          ON qu.user_name = quc.user_name
          WHERE quc.contribution > 0
          ORDER BY quc.contribution DESC, quc.articles_count DESC
          LIMIT $limit OFFSET $offset;
       """
      .map(toQiitaUser)
      .list()
      .apply()
  }

  override def countArticlesCount()(implicit session: DBSession = AutoSession): Long = {
    sql"""
          SELECT COUNT(user_name) FROM qiita_user_contributions
          WHERE articles_count > 0;
       """
      .map(_.long(1))
      .single()
      .apply()
      .get
  }

  override def retrieveArticlesCount(limit: Int, offset: Int)(implicit session: DBSession = AutoSession): List[QiitaUser] = {
    sql"""
          SELECT * FROM qiita_users AS qu
          INNER JOIN qiita_user_contributions AS quc
          ON qu.user_name = quc.user_name
          WHERE quc.articles_count > 0
          ORDER BY quc.articles_count DESC, quc.contribution DESC
          LIMIT $limit OFFSET $offset;
       """
      .map(toQiitaUser)
      .list()
      .apply()
  }

  private def toQiitaUser(rs: WrappedResultSet): QiitaUser = {
    QiitaUser(
      QiitaUserProfile(
        QiitaUserId(rs.int("qiita_user_id")),
        QiitaUserName(rs.string("user_name")),
        ProfileImageUrl(rs.string("profile_image_url"))
      ),
      QiitaUserContribution(
        Contribution(rs.int("contribution")),
        ArticlesCount(rs.int("articles_count")),
        HatenaCount(rs.int("hatena_count"))
      )
    )
  }
}
