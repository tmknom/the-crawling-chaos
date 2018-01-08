package infrastructure.qiita.user.json

import javax.inject.Singleton

import domain.qiita.user.QiitaUserName
import domain.qiita.user.json.{QiitaRawInternalUserJsonRepository, RawInternalUserJson}
import scalikejdbc._

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments", "org.wartremover.warts.Nothing"))
@Singleton
final class ScalikejdbcQiitaRawInternalUserJsonRepository extends QiitaRawInternalUserJsonRepository {
  override def register(qiitaUserName: QiitaUserName, rawInternalUserJson: RawInternalUserJson)(implicit session: DBSession = AutoSession): Unit = {
    val name    = qiitaUserName.value
    val rawJson = rawInternalUserJson.value

    sql"""
          INSERT INTO raw_qiita_internal_user_jsons (user_name, raw_json)
          VALUES ($name, $rawJson);
       """
      .update()
      .apply()

    () // 明示的に Unit を返す
  }
}
