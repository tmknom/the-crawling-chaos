package fixture.db.qiita

import com.ninja_squad.dbsetup.Operations
import com.ninja_squad.dbsetup.operation.Operation

/**
  * qiita_users テーブルのフィクスチャ
  */
object QiitaUsersTableFixture {

  val Table = "qiita_users"

  object Column {
    val Id       = "id"
    val UserName = "user_name"
  }

  object Default {
    val Id       = "1"
    val UserName = "jojo"

    val One: Operation = {
      Operations
        .insertInto(Table)
        .columns(Column.Id, Column.UserName)
        .values(Id, UserName)
        .build()
    }

    val List: Operation = {
      Operations
        .insertInto(Table)
        .columns(Column.Id, Column.UserName)
        .values(Id, UserName)
        .values("2", "dio")
        .values("3", "kira")
        .build()
    }
  }

}
