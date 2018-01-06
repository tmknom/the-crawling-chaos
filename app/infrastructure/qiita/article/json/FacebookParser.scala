package infrastructure.qiita.article.json

import domain.qiita.article.contribution.FacebookCount
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.scraper.ContentExtractors._

private[json] final case class FacebookParser(html: String) {
  def parse: FacebookCount = {
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
