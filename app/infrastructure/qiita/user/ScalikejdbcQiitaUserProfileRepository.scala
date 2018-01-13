package infrastructure.qiita.user

import javax.inject.Singleton

import domain.qiita.user._
import scalikejdbc._

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments", "org.wartremover.warts.Nothing"))
@Singleton
final class ScalikejdbcQiitaUserProfileRepository extends QiitaUserProfileRepository {
  override def register(qiitaUserProfile: QiitaUserProfile)(implicit session: DBSession = AutoSession): Unit = {
    val name            = qiitaUserProfile.name.value
    val id              = qiitaUserProfile.id.value
    val profileImageUrl = qiitaUserProfile.profileImageUrl.value

    sql"""
          INSERT INTO qiita_users (user_name, qiita_user_id, profile_image_url)
          VALUES ($name, $id, $profileImageUrl);
       """
      .update()
      .apply()

    () // 明示的に Unit を返す
  }

  override def retrieve(qiitaUserName: QiitaUserName)(implicit session: DBSession = AutoSession): Option[QiitaUserProfile] = {
    val name = qiitaUserName.value

    sql"""
          SELECT * FROM qiita_users
          WHERE user_name = $name ;
       """
      .map(toQiitaUserProfile)
      .single()
      .apply()
  }

  private def toQiitaUserProfile(rs: WrappedResultSet): QiitaUserProfile = {
    QiitaUserProfile(
      QiitaUserId(rs.int("qiita_user_id")),
      QiitaUserName(rs.string("user_name")),
      ProfileImageUrl(rs.string("profile_image_url"))
    )
  }
}
