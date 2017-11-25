package infrastructure.qiita.user

import domain.qiita.user.QiitaUserName
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._

private[user] final case class QiitaUserParser(html: String) {
  def parse: Seq[QiitaUserName] = {
    val doc = JsoupBrowser().parseString(html)
    val items = doc >> elementList("#main .js-hovercard")
    items.map { item =>
      val name = item >> attr("data-hovercard-target-name")
      QiitaUserName(name)
    }
  }
}
