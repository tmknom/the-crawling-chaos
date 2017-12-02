package fixture.db.qiita

import com.ninja_squad.dbsetup.Operations
import com.ninja_squad.dbsetup.operation.Operation

/**
  * qiita_users テーブルのフィクスチャ
  */
object QiitaUsersFixture {

  private val Table: String = "qiita_users"

  private object Column {
    val Id:       String = "id"
    val UserName: String = "user_name"
  }

  object Default {
    val Id:       String = "1"
    val UserName: String = "jojo"

    val Fixtures: Operation = {
      Operations
        .insertInto(Table)
        .columns(Column.Id, Column.UserName)
        .values(Id, UserName)
        .build()
    }
  }

  object List {
    val Fixtures: Operation = {
      Operations
        .insertInto(Table)
        .columns(Column.Id, Column.UserName)
        .values(Default.Id, Default.UserName)
        .values("2", "dio")
        .values("3", "kira")
        .build()
    }
  }

}
