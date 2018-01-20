package domain.crawler

import domain.Identifier
import play.api.Logger

import scala.collection.mutable

trait QuietlyExecution {
  def withSleep[T](identifier: Identifier[T], progress: String, errors: mutable.ListBuffer[String])(f: (Identifier[T]) => Unit): Unit = {
    executeQuietly(identifier, progress, errors) { (_) =>
      f(identifier)
    }
    Sleeper.sleep()
  }

  def withoutSleep[T](identifier: Identifier[T], progress: String, errors: mutable.ListBuffer[String])(f: (Identifier[T]) => Unit): Unit = {
    executeQuietly(identifier, progress, errors) { (_) =>
      f(identifier)
    }
  }

  private def executeQuietly[T](identifier: Identifier[T], progress: String, errors: mutable.ListBuffer[String])(f: (Identifier[T]) => Unit): Unit = {
    try {
      f(identifier)
      Logger.info(s"${this.getClass.getSimpleName} executed ${identifier.value} $progress")
    } catch {
      case e: Exception =>
        errors += identifier.value.toString
        Logger.warn(s"${this.getClass.getSimpleName} executed error ${identifier.value}.", e)
    }
  }
}
