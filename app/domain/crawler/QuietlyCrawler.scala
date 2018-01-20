package domain.crawler

import domain.Identifier
import play.api.Logger

import scala.collection.mutable

trait QuietlyCrawler {
  def withQuietly[T](identifier: Identifier[T], progress: String, errors: mutable.ListBuffer[T])(f: (Identifier[T]) => Unit): Unit = {
    try {
      f(identifier)
      Logger.info(s"${this.getClass.getSimpleName} crawled ${identifier.value} $progress")
    } catch {
      case e: Exception =>
        errors += identifier.value
        Logger.warn(s"${this.getClass.getSimpleName} crawl error ${identifier.value}.", e)
    } finally {
      Sleeper.sleep()
    }
  }
}
