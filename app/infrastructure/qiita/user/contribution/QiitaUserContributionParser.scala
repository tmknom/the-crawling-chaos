package infrastructure.qiita.user.contribution

import domain.qiita.user.contribution.QiitaUserContribution
import spray.json._

private[contribution] final case class QiitaUserContributionParser(json: String) {
  @SuppressWarnings(Array("org.wartremover.warts.TraversableOps"))
  def parse: QiitaUserContribution = {
    QiitaUserContribution(json.parseJson.asJsObject.getFields("contribution").head.toString.toInt)
  }
}
