package infrastructure.qiita.initial

import java.util.concurrent.TimeUnit

import domain.qiita.initial.{Initial, Page, QiitaUserInitial, QiitaUserInitialGateway}
import library.adaptor.DispatchAdaptor
import play.api.Logger

import scala.collection.mutable

final class HttpQiitaUserInitialGateway extends QiitaUserInitialGateway {
  private val BaseUrl = "https://qiita.com/users?char="

  override def fetch(): Seq[QiitaUserInitial] = {
    @SuppressWarnings(Array("org.wartremover.warts.MutableDataStructures"))
    val qiitaUserInitials = mutable.ArrayBuffer.empty[QiitaUserInitial]

    initials.foreach { initial =>
      Logger.info(s"start request $initial")
      qiitaUserInitials += qiitaUserInitial(initial)
      TimeUnit.SECONDS.sleep(1)
    }

    qiitaUserInitials
  }

  private def qiitaUserInitial(initial: Char) = {
    QiitaUserInitial(
      Initial(initial),
      Page(page(initial))
    )
  }

  private def page(initial: Char) = {
    val url      = BaseUrl + initial.toString
    val response = DispatchAdaptor(url).request()
    QiitaUserInitialParser(response).parse
  }

  private def initials: mutable.ArrayBuffer[Char] = {
    @SuppressWarnings(Array("org.wartremover.warts.MutableDataStructures"))
    val result = mutable.ArrayBuffer.empty[Char]

    ('A' to 'Z').foreach(result += _)
    ('0' to '9').foreach(result += _)
    result += '_'
    result
  }
}
