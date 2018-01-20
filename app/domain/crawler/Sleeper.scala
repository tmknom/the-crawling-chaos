package domain.crawler

import java.util.concurrent.TimeUnit

object Sleeper {
  private val SleepTimeMilliseconds = 100.toLong

  def sleep(): Unit = {
    TimeUnit.MILLISECONDS.sleep(SleepTimeMilliseconds)
  }
}
