package domain.qiita.user

import java.time.{LocalDate, ZonedDateTime}

import library.datetime.DateTimeProvider

final case class RegisteredDateTime(value: ZonedDateTime) {
  def toLocalDate: LocalDate = {
    value.toLocalDate
  }
}

object RegisteredDateTime {
  def now(): RegisteredDateTime = {
    new RegisteredDateTime(DateTimeProvider.nowJST())
  }
}
