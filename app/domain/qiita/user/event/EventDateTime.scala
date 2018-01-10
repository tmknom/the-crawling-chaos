package domain.qiita.user.event

import java.time.{LocalDate, ZonedDateTime}

import library.datetime.DateTimeProvider

final case class EventDateTime(value: ZonedDateTime) {
  def toLocalDate: LocalDate = {
    value.toLocalDate
  }
}

object EventDateTime {
  def now(): EventDateTime = {
    EventDateTime(DateTimeProvider.nowJST())
  }
}
