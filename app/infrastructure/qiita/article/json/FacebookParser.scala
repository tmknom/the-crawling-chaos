package infrastructure.qiita.article.json

import domain.qiita.article.contribution.FacebookCount
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.scraper.ContentExtractors._
import play.api.Logger

private[json] final case class FacebookParser(html: String) {
  def parse: FacebookCount = {
    try {
      parseThrowable
    } catch {
      case e: Exception => {
        Logger.error(e.getMessage, e)
        FacebookCount(0)
      }
    }
  }

  private def parseThrowable: FacebookCount = {
    val doc = JsoupBrowser().parseString(html)
    val raw = doc >> text("td._2pir #u_0_3 span")

    val pattern = "([0-9]+).*".r
    val facebookCount = raw match {
      case pattern(v) => v.toString.toInt
      case _          => 0
    }

    FacebookCount(facebookCount)
  }
}
