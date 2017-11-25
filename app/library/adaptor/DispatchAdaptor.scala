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
    val future: Future[String] = Http.default(url(uri) OK as.String)
    Await.result(future, Timeout)
  }
}

object DispatchAdaptor {

  /**
    * アプリケーションの実行終了時にクローズする
    */
  def close(): Unit = {
    Http.default.client.close()
  }
}
