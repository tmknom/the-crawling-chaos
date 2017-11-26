package infrastructure.qiita.userranking

import domain.qiita.userranking.{QiitaUserRanking, QiitaUserRankingContribution, QiitaUserRankingId, QiitaUserRankingName}
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.Element

private[userranking] final case class QiitaUserRankingParser(html: String) {
  def parse: Seq[QiitaUserRanking] = {
    val SkipElement = 4
    val doc         = JsoupBrowser().parseString(html)
    val items       = doc >> elementList("main p")
    items.drop(SkipElement).map(qiitaUserRanking)
  }

  private def qiitaUserRanking(item: Element): QiitaUserRanking = {
    QiitaUserRanking(
      id           = QiitaUserRankingId(-1),
      name         = QiitaUserRankingName(item >> element("a") >> text),
      contribution = QiitaUserRankingContribution(item.innerHtml.split(", ").last.toInt)
    )
  }
}
