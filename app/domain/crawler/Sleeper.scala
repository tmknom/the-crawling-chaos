package domain.crawler

import java.util.concurrent.TimeUnit

object Sleeper {
  private val SleepTimeMilliseconds = 300.toLong

  def sleep(): Unit = {
    TimeUnit.MILLISECONDS.sleep(SleepTimeMilliseconds)
  }
}
