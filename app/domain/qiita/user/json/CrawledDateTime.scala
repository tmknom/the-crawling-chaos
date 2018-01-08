package domain.qiita.user.json

import java.time.ZonedDateTime

import library.datetime.DateTimeProvider

final case class CrawledDateTime(value: ZonedDateTime)

object CrawledDateTime {
  def now(): CrawledDateTime = {
    new CrawledDateTime(DateTimeProvider.nowJST())
  }
}
