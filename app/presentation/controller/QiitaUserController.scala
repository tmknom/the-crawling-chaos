package presentation.controller

import javax.inject.Inject

import application.qiita.user.QiitaUserApplication
import play.api.mvc._

// scalastyle:off
class QiitaUserController @Inject()(cc: ControllerComponents, application: QiitaUserApplication) extends AbstractController(cc) {
  def list(): Action[AnyContent] = Action {
    val result = application.list()
    Ok(result.toString())
  }
}
