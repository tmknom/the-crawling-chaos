package infrastructure.qiita.article

import domain.qiita.article.QiitaItemId
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._

private[article] final case class QiitaArticleIdParser(html: String) {
  def parse: List[QiitaItemId] = {
    val doc   = JsoupBrowser().parseString(html)
    val items = doc >> elementList("#main article")

    items.map { item =>
      QiitaItemId(item >> attr("data-uuid"))
    }
  }
}
