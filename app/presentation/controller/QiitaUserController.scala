package presentation.controller

import javax.inject.Inject

import application.qiita.user.QiitaUserApplication
import domain.qiita.user.QiitaUser
import play.api.mvc._
import presentation.controller.internal.MapJsonProtocol._
import spray.json._

class QiitaUserController @Inject()(cc: ControllerComponents, application: QiitaUserApplication) extends AbstractController(cc) {
  def list(): Action[AnyContent] = Action {
    val result = application.list().map(QiitaUserJson.build)
    Ok(result.toJson.prettyPrint).as(JSON)
  }
}

object QiitaUserJson {
  def build(qiitaUser: QiitaUser): Map[String, _] = {
    Map(
      "id" -> qiitaUser.profile.id.value,
      "name" -> qiitaUser.profile.name.value,
      "contribution" -> qiitaUser.contribution.value,
      "articles_count" -> qiitaUser.articlesCount.value
    )
  }
}
