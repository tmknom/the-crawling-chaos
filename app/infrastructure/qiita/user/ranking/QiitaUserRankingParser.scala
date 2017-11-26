package infrastructure.qiita.user.ranking

import domain.qiita.user.ranking.{QiitaUserRanking, QiitaUserRankingContribution}
import domain.qiita.user.{QiitaUserId, QiitaUserName}
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.Element

private[ranking] final case class QiitaUserRankingParser(html: String) {
  def parse: Seq[QiitaUserRanking] = {
    val SkipElement = 4
    val doc         = JsoupBrowser().parseString(html)
    val items       = doc >> elementList("main p")
    items.drop(SkipElement).map(qiitaUserRanking)
  }

  private def qiitaUserRanking(item: Element): QiitaUserRanking = {
    QiitaUserRanking(
      qiitaUserId  = QiitaUserId(-1),
      name         = QiitaUserName(item >> element("a") >> text),
      contribution = QiitaUserRankingContribution(item.innerHtml.split(", ").last.toInt)
    )
  }
}
