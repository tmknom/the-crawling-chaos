package fixture.db.qiita

import com.ninja_squad.dbsetup.Operations
import com.ninja_squad.dbsetup.operation.Operation

/**
  * qiita_users テーブルのフィクスチャ
  */
object QiitaUsersFixture {

  private val Table: String = "qiita_users"

  private object Column {
    val UserName:        String = "user_name"
    val QiitaUserId:     String = "qiita_user_id"
    val ProfileImageUrl: String = "profile_image_url"
  }

  object Default {
    val UserName:        String = "jojo"
    val QiitaUserId:     String = "10"
    val ProfileImageUrl: String = "http://localhost/image.jpg"

    val Fixtures: Operation = {
      Operations
        .insertInto(Table)
        .columns(Column.UserName, Column.QiitaUserId, Column.ProfileImageUrl)
        .values(UserName, QiitaUserId, ProfileImageUrl)
        .build()
    }
  }

}
