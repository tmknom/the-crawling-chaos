package fixture.db.qiita

import com.ninja_squad.dbsetup.Operations
import com.ninja_squad.dbsetup.operation.Operation

/**
  * qiita_user_contributions テーブルのフィクスチャ
  */
object QiitaUserContributionsFixture {

  private val Table: String = "qiita_user_contributions"

  private object Column {
    val QiitaUserId:  String = "qiita_user_id"
    val Contribution: String = "contribution"
  }

  object Default {
    val QiitaUserId:  String = QiitaUsersFixture.Default.Id
    val Contribution: String = "100"

    val Fixtures: Operation = {
      Operations
        .insertInto(Table)
        .columns(Column.QiitaUserId, Column.Contribution)
        .values(QiitaUserId, Contribution)
        .build()
    }
  }

}
