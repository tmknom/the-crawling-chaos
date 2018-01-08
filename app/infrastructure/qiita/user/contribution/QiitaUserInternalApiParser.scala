package infrastructure.qiita.user.contribution

import domain.qiita.user.contribution.{ArticlesCount, QiitaUserContribution}
import spray.json._

private[contribution] final case class QiitaUserInternalApiParser(json: String) {
  @SuppressWarnings(Array("org.wartremover.warts.TraversableOps"))
  def parse: (QiitaUserContribution, ArticlesCount) = {
    val contribution  = json.parseJson.asJsObject.getFields("contribution").head.toString.toInt
    val articlesCount = json.parseJson.asJsObject.getFields("articles_count").head.toString.toInt

    (QiitaUserContribution(contribution), ArticlesCount(articlesCount))
  }
}
