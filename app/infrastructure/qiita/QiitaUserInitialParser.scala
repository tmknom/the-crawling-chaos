package infrastructure.qiita

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._

private[qiita] final case class QiitaUserInitialParser(html: String) {

  @SuppressWarnings(Array("org.wartremover.warts.TraversableOps"))
  def parse: Int = {
    val doc         = JsoupBrowser().parseString(html)
    val paginations = doc >> elementList("#main .pagination li")
    val href        = paginations.last >> element("a") >> attr("href")
    href.split("=").last.toInt // /users?char=A&page=107 みたいな文字列が取れてるはず
  }
}
