package fixture.db.qiita

import java.time.{ZoneId, ZonedDateTime}

import com.ninja_squad.dbsetup.Operations
import com.ninja_squad.dbsetup.operation.Operation

/**
  * deprecated_qiita_users テーブルのフィクスチャ
  */
object DeprecatedQiitaUsersFixture {

  private val Table: String = "deprecated_qiita_users"

  private object Column {
    val Id:                 String = "id"
    val UserName:           String = "user_name"
    val RegisteredDateTime: String = "registered_date_time"
  }

  object Default {
    val Id:                 String        = "1"
    val UserName:           String        = "jojo"
    val RegisteredDateTime: ZonedDateTime = ZonedDateTime.of(2017, 12, 31, 23, 59, 59, 0, ZoneId.of("Asia/Tokyo")) // scalastyle:off

    val Fixtures: Operation = {
      Operations
        .insertInto(Table)
        .columns(Column.Id, Column.UserName, Column.RegisteredDateTime)
        .values(Id, UserName, RegisteredDateTime)
        .build()
    }
  }

  object List {
    val Fixtures: Operation = {
      Operations
        .insertInto(Table)
        .columns(Column.Id, Column.UserName, Column.RegisteredDateTime)
        .values(Default.Id, Default.UserName, Default.RegisteredDateTime)
        .values("2", "dio", ZonedDateTime.of(2018, 1, 1, 12, 34, 56, 0, ZoneId.of("Asia/Tokyo")))
        .values("3", "kira", ZonedDateTime.of(2018, 1, 2, 12, 34, 56, 0, ZoneId.of("Asia/Tokyo")))
        .build()
    }
  }

}
