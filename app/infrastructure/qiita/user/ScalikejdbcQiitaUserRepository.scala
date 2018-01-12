package infrastructure.qiita.user

import javax.inject.Singleton

import domain.qiita.user._
import scalikejdbc._

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments", "org.wartremover.warts.Nothing"))
@Singleton
final class ScalikejdbcQiitaUserRepository extends QiitaUserRepository {
  override def register(qiitaUser: QiitaUser)(implicit session: DBSession = AutoSession): Unit = {
    val name            = qiitaUser.name.value
    val id              = qiitaUser.id.value
    val profileImageUrl = qiitaUser.profileImageUrl.value

    sql"""
          INSERT INTO qiita_users (user_name, qiita_user_id, profile_image_url)
          VALUES ($name, $id, $profileImageUrl);
       """
      .update()
      .apply()

    () // 明示的に Unit を返す
  }

  override def retrieve(qiitaUserName: QiitaUserName)(implicit session: DBSession = AutoSession): Option[QiitaUser] = {
    val name = qiitaUserName.value

    sql"""
          SELECT * FROM qiita_users
          WHERE user_name = $name ;
       """
      .map(toQiitaUser)
      .single()
      .apply()
  }

  private def toQiitaUser(rs: WrappedResultSet): QiitaUser = {
    QiitaUser(
      QiitaUserId(rs.int("qiita_user_id")),
      QiitaUserName(rs.string("user_name")),
      ProfileImageUrl(rs.string("profile_image_url"))
    )
  }
}
