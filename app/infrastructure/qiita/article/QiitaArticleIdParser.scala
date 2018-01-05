package infrastructure.qiita.article

import domain.qiita.article.{QiitaArticleId, _}
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._

private[article] final case class QiitaArticleIdParser(html: String) {
  private val PrefixUrl = "https://qiita.com"

  private val UndefinedQiitaArticleId = QiitaArticleId(-1)

  def parse: List[QiitaArticle] = {
    val doc   = JsoupBrowser().parseString(html)
    val items = doc >> elementList("#main article")

    items.map { item =>
      val itemId = QiitaItemId(item >> attr("data-uuid"))
      val title  = QiitaArticleTitle(item >> text(".ItemLink__title a"))
      val url    = QiitaArticleUrl(PrefixUrl + (item >> attr("data-item-url")))
      QiitaArticle(UndefinedQiitaArticleId, itemId, title, url)
    }
  }
}
