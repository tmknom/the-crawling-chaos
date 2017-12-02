package fixture.db.qiita

import com.ninja_squad.dbsetup.Operations
import com.ninja_squad.dbsetup.operation.Operation

/**
  * qiita_user_contributions テーブルのフィクスチャ
  */
object QiitaUserContributionsTableFixture {

  val Table = "qiita_user_contributions"

  object Column {
    val QiitaUserId  = "qiita_user_id"
    val Contribution = "contribution"
  }

  object Default {
    val QiitaUserId  = QiitaUsersTableFixture.Default.Id
    val Contribution = "100"

    val One: Operation = {
      Operations
        .insertInto(Table)
        .columns(Column.QiitaUserId, Column.Contribution)
        .values(QiitaUserId, Contribution)
        .build()
    }
  }

}
