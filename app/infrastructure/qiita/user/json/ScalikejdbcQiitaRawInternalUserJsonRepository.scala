package infrastructure.qiita.user.json

import javax.inject.Singleton

import domain.qiita.user.QiitaUserName
import domain.qiita.user.json.{CrawledDateTime, QiitaRawInternalUserJsonRepository, RawInternalUserJson}
import scalikejdbc._

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments", "org.wartremover.warts.Nothing"))
@Singleton
final class ScalikejdbcQiitaRawInternalUserJsonRepository extends QiitaRawInternalUserJsonRepository {
  override def register(qiitaUserName: QiitaUserName, rawInternalUserJson: RawInternalUserJson, crawledDateTime: CrawledDateTime)(implicit session: DBSession =
                                                                                                                                    AutoSession): Unit = {
    val name    = qiitaUserName.value
    val rawJson = rawInternalUserJson.value
    val crawled = crawledDateTime.value

    sql"""
          INSERT INTO raw_qiita_internal_user_jsons (user_name, raw_json, crawled_date_time)
          VALUES ($name, $rawJson, $crawled);
       """
      .update()
      .apply()

    () // 明示的に Unit を返す
  }

  override def retrieve(qiitaUserName: QiitaUserName)(implicit session: DBSession = AutoSession): Option[RawInternalUserJson] = {
    val name = qiitaUserName.value

    sql"""
          SELECT raw_json FROM raw_qiita_internal_user_jsons
          WHERE user_name = $name;
       """
      .map(toRawInternalUserJson)
      .single()
      .apply()
  }

  override def retrieveRecently()(implicit session: DBSession = AutoSession): List[QiitaUserName] = {
    sql"""
          SELECT user_name FROM raw_qiita_internal_user_jsons AS r
          WHERE NOT EXISTS
          (SELECT 1 FROM qiita_users AS qu WHERE r.user_name = qu.user_name)
          ORDER BY user_name ASC;
       """
      .map(toQiitaUserName)
      .list()
      .apply()
  }

  private def toRawInternalUserJson(rs: WrappedResultSet): RawInternalUserJson = {
    RawInternalUserJson(rs.string("raw_json"))
  }

  private def toQiitaUserName(rs: WrappedResultSet): QiitaUserName = {
    QiitaUserName(rs.string("user_name"))
  }
}
