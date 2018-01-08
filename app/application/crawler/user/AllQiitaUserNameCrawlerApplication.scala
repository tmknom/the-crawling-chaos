package application.crawler.user

import java.util.concurrent.TimeUnit
import javax.inject.{Inject, Singleton}

import play.api.Logger

@Singleton
final class AllQiitaUserNameCrawlerApplication @Inject()(
    ) {
  private val SleepTimeMilliseconds = 100.toLong

  def crawl(): Unit = {
    Logger.info(s"${this.getClass.getSimpleName} crawled")
    TimeUnit.MILLISECONDS.sleep(SleepTimeMilliseconds)
  }
}
