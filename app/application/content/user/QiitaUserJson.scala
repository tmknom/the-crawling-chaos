package application.content.user

import domain.qiita.user.QiitaUser

object QiitaUserJson {
  def build(qiitaUser: QiitaUser, rank: Int): Map[String, Any] = {
    Map(
      "rank" -> rank,
      "name" -> qiitaUser.profile.name.value,
      "profile_image_url" -> qiitaUser.profile.profileImageUrl.value,
      "total" -> qiitaUser.qiitaUserContribution.totalEvaluation.value,
      "contribution" -> qiitaUser.qiitaUserContribution.contribution.value,
      "hatena_count" -> qiitaUser.qiitaUserContribution.hatenaCount.value,
      "articles_count" -> qiitaUser.qiitaUserContribution.articlesCount.value,
      "contribution_average" -> qiitaUser.qiitaUserContribution.contributionAverage.value,
      "hatenaCount_average" -> qiitaUser.qiitaUserContribution.hatenaCountAverage.value
    )
  }
}
