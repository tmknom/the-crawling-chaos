package infrastructure.qiita.user

import domain.qiita.user.{QiitaUser, QiitaUserId, QiitaUserName}
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._

private[user] final case class QiitaUserParser(html: String) {
  def parse: Seq[QiitaUser] = {
    val doc   = JsoupBrowser().parseString(html)
    val items = doc >> elementList("#main .js-hovercard")
    items.map { item =>
      val name = item >> attr("data-hovercard-target-name")
      QiitaUser(QiitaUserId(-1), QiitaUserName(name))
    }
  }
}
