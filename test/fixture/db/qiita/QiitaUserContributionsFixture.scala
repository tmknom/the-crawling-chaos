package fixture.db.qiita

import java.time.{ZoneId, ZonedDateTime}

import com.ninja_squad.dbsetup.Operations
import com.ninja_squad.dbsetup.operation.Operation

/**
  * qiita_user_contributions テーブルのフィクスチャ
  */
object QiitaUserContributionsFixture {

  private val Table: String = "qiita_user_contributions"

  private object Column {
    val QiitaUserId:     String = "qiita_user_id"
    val Contribution:    String = "contribution"
    val UpdatedDateTime: String = "updated_date_time"
  }

  object Default {
    val QiitaUserId:     String        = QiitaUsersFixture.Default.Id
    val Contribution:    String        = "100"
    val UpdatedDateTime: ZonedDateTime = ZonedDateTime.of(2017, 12, 31, 23, 59, 59, 0, ZoneId.of("Asia/Tokyo")) // scalastyle:off

    val Fixtures: Operation = {
      Operations
        .insertInto(Table)
        .columns(Column.QiitaUserId, Column.Contribution, Column.UpdatedDateTime)
        .values(QiitaUserId, Contribution, UpdatedDateTime)
        .build()
    }
  }

}
