package presentation.controller

import javax.inject.Inject

import application.qiita.user.QiitaUserContributionApplication
import domain.qiita.user.summary.QiitaUserSummary
import library.spray.SnakeCaseJsonNaming
import play.api.mvc._
import spray.json._

class QiitaUserContributionController @Inject()(cc: ControllerComponents, application: QiitaUserContributionApplication) extends AbstractController(cc) {
  def list(): Action[AnyContent] = Action {
    import QiitaUserSummaryResponse._
    val result = application.list().map(toResponse)
    Ok(result.toJson.prettyPrint).as(JSON)
  }
}

object QiitaUserSummaryResponse extends SnakeCaseJsonNaming {

  final case class QiitaUserSummaryResponse(id: Int, name: String, contribution: Int)

  def toResponse(qiitaUserSummary: QiitaUserSummary): QiitaUserSummaryResponse = {
    QiitaUserSummaryResponse(
      qiitaUserSummary.id.value,
      qiitaUserSummary.name.value,
      qiitaUserSummary.contribution.value
    )
  }

  implicit val format: RootJsonFormat[QiitaUserSummaryResponse] = jsonFormat3(QiitaUserSummaryResponse)
}
