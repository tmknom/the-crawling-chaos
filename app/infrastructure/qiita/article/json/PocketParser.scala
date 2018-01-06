package infrastructure.qiita.article.json

import domain.qiita.article.contribution.PocketCount
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.scraper.ContentExtractors._

private[json] final case class PocketParser(html: String) {
  def parse: PocketCount = {
    val doc = JsoupBrowser().parseString(html)
    val raw = doc >> text("#cnt")
    PocketCount(raw.toInt)
  }
}
