package infrastructure.qiita.user

import javax.inject.Singleton

import domain.qiita.user.{QiitaUserName, QiitaUserNameRepository}
import scalikejdbc._

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments", "org.wartremover.warts.Nothing"))
@Singleton
final class ScalikejdbcQiitaUserNameRepository extends QiitaUserNameRepository {
  override def register(qiitaUserName: QiitaUserName)(implicit session: DBSession = AutoSession): Unit = {
    val userName = qiitaUserName.value
    sql"""
          INSERT INTO qiita_user_names (user_name)
          VALUES ($userName);
       """
      .update()
      .apply()

    () // 明示的に Unit を返す
  }

  def retrieveRecently()(implicit session: DBSession = AutoSession): List[QiitaUserName] = {
    sql"""
          SELECT user_name FROM qiita_user_names AS qun
          WHERE NOT EXISTS
          (SELECT 1 FROM raw_qiita_internal_user_jsons AS r WHERE qun.user_name = r.user_name)
          ORDER BY user_name ASC;
       """
      .map(toQiitaUserName)
      .list()
      .apply()
  }

  private def toQiitaUserName(rs: WrappedResultSet): QiitaUserName = {
    QiitaUserName(rs.string("user_name"))
  }
}
