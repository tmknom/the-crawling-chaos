package infrastructure.qiita.article

import domain.qiita.article.json.RawArticleJson
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.scraper.ContentExtractors._

private[article] final case class QiitaArticleInternalApiParser(html: String) {
  def parse: RawArticleJson = {
    val doc  = JsoupBrowser().parseString(html)
    val json = doc >> element("#main article .p-items_article") >> attr("data-article-props")
    RawArticleJson(json)
  }
}
