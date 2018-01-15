package domain.qiita.article

import java.time.{Instant, ZonedDateTime}

import library.datetime.DateTimeProvider

final case class QiitaArticlePostedDateTime(value: ZonedDateTime)

object QiitaArticlePostedDateTime {
  def apply(unixTime: Int): QiitaArticlePostedDateTime = {
    val instant       = Instant.ofEpochSecond(unixTime)
    val zonedDateTime = ZonedDateTime.ofInstant(instant, DateTimeProvider.JST)
    QiitaArticlePostedDateTime(zonedDateTime)
  }
}
