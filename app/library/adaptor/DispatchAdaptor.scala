package library.adaptor

import dispatch.Defaults._
import dispatch._

import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}

/**
  * HTTP 通信を担う Dispatch ライブラリのアダプタ
  */
final case class DispatchAdaptor(uri: String) {
  private val Timeout = 30.seconds

  def request(): String = {
    try {
      val future: Future[String] = Http.default(url(uri) OK as.String)
      Await.result(future, Timeout)
    } finally {
      Http.default.client.close() // これがないと JVM がシャットダウンせず、プロセスが刺さりっぱなしになる
    }
  }
}
