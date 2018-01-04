package domain.qiita.user.contribution

import java.time.ZonedDateTime

import library.datetime.DateTimeProvider

final case class UpdatedDateTime(value: ZonedDateTime)

object UpdatedDateTime {
  def now(): UpdatedDateTime = {
    UpdatedDateTime(DateTimeProvider.nowJST())
  }
}
