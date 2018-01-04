package domain.qiita.user

import java.time.ZonedDateTime

import library.datetime.DateTimeProvider

final case class RegisteredDateTime(value: ZonedDateTime)

object RegisteredDateTime {
  def now(): RegisteredDateTime = {
    new RegisteredDateTime(DateTimeProvider.nowJST())
  }
}
