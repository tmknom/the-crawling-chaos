package domain.qiita.user

import java.time.{LocalDate, ZonedDateTime}

import library.datetime.DateTimeProvider

final case class CrawledDateTime(value: ZonedDateTime) {
  def toLocalDate: LocalDate = {
    value.toLocalDate
  }
}

object CrawledDateTime {
  def now(): CrawledDateTime = {
    new CrawledDateTime(DateTimeProvider.nowJST())
  }
}
