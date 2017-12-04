package presentation.controller

import javax.inject.Inject

import application.qiita.user.QiitaUserContributionApplication
import play.api.mvc._

class QiitaUserContributionController @Inject()(cc: ControllerComponents, application: QiitaUserContributionApplication) extends AbstractController(cc) {
  def list(): Action[AnyContent] = Action {
    val reuslt = application.list()
    Ok("ok")
  }
}
