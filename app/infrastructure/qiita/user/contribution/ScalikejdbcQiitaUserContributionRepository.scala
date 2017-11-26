package infrastructure.qiita.user.contribution

import domain.qiita.user.contribution.QiitaUserContributionRepository
import scalikejdbc._

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments", "org.wartremover.warts.Nothing"))
final class ScalikejdbcQiitaUserContributionRepository extends QiitaUserContributionRepository {}
