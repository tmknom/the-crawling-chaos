package domain.qiita.user.initial

import scala.collection.mutable

final case class Initials(value: List[Initial])

object Initials {
  def all(): Initials = {
    Initials(listInitialChar.map(Initial))
  }

  private def listInitialChar: List[Char] = {
    @SuppressWarnings(Array("org.wartremover.warts.MutableDataStructures"))
    val result = mutable.ArrayBuffer.empty[Char]

    ('A' to 'Z').foreach(result += _)
    ('0' to '9').foreach(result += _)
    result += '_'
    result.toList
  }
}
