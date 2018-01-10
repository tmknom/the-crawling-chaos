package infrastructure.qiita.user

import javax.inject.Singleton

import domain.qiita.user._
import scalikejdbc._

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments", "org.wartremover.warts.Nothing"))
@Singleton
final class ScalikejdbcDeprecatedQiitaUserRepository extends DeprecatedQiitaUserRepository {
  override def register(qiitaUserName: QiitaUserName, registeredDateTime: RegisteredDateTime)(implicit session: DBSession = AutoSession): Unit = {
    val userName   = qiitaUserName.value
    val registered = registeredDateTime.value
    sql"""
          INSERT INTO deprecated_qiita_users (user_name, registered_date_time)
          VALUES ($userName, $registered);
       """
      .update()
      .apply()

    () // 明示的に Unit を返す
  }

  override def delete(qiitaUserId: QiitaUserId)(implicit session: DBSession = AutoSession): Unit = {
    val id = qiitaUserId.value
    sql"""
          DELETE FROM deprecated_qiita_users
          WHERE id = $id;
       """
      .update()
      .apply()

    () // 明示的に Unit を返す
  }

  override def retrieveRecently()(implicit session: DBSession = AutoSession): Seq[DeprecatedQiitaUser] = {
    sql"""
          SELECT id, user_name, registered_date_time FROM deprecated_qiita_users AS qu
          WHERE NOT EXISTS
          (SELECT 1 FROM deprecated_qiita_user_contributions AS quc WHERE qu.id = quc.qiita_user_id)
          ORDER BY id ASC;
       """
      .map(toQiitaUser)
      .list()
      .apply()
  }

  override def retrieveContributed()(implicit session: DBSession = AutoSession): Seq[DeprecatedQiitaUser] = {
    sql"""
          SELECT qu.id, qu.user_name, qu.registered_date_time FROM deprecated_qiita_users AS qu
          INNER JOIN deprecated_qiita_user_contributions AS quc ON qu.id = quc.qiita_user_id
          WHERE quc.contribution > 0
          ORDER BY quc.contribution DESC;
       """
      .map(toQiitaUser)
      .list()
      .apply()
  }

  override def retrieveTop1000()(implicit session: DBSession = AutoSession): Seq[DeprecatedQiitaUser] = {
    sql"""
          SELECT qu.id, qu.user_name, qu.registered_date_time FROM deprecated_qiita_users AS qu
          INNER JOIN deprecated_qiita_user_contributions AS quc ON qu.id = quc.qiita_user_id
          ORDER BY quc.contribution DESC LIMIT 1000;
       """
      .map(toQiitaUser)
      .list()
      .apply()
  }

  override def retrieveUnavailable()(implicit session: DBSession = AutoSession): Seq[DeprecatedQiitaUser] = {
    // 事前に RecentlyQiitaUserContributionCrawlerCli を実行していることを前提とすると
    // deprecated_qiita_user_contributions テーブルにレコードがない = 無効なユーザであると判断できる。
    // よって、単純に retrieveRecently メソッドを呼ぶだけとしている。
    retrieveRecently()
  }

  private def toQiitaUser(rs: WrappedResultSet): DeprecatedQiitaUser = {
    DeprecatedQiitaUser(
      QiitaUserId(rs.int("id")),
      QiitaUserName(rs.string("user_name")),
      RegisteredDateTime(rs.zonedDateTime("registered_date_time"))
    )
  }
}
