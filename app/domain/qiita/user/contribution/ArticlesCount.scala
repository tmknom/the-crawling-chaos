package domain.qiita.user.contribution

import domain.qiita.article.contribution.HatenaCount

/**
  * 記事数
  */
final case class ArticlesCount(value: Int) {
  def contributionAverage(contribution: Contribution): ContributionAverage = {
    if (isZero) {
      ContributionAverage(0)
    } else {
      ContributionAverage(contribution.value / value)
    }
  }

  def hatenaCountAverage(hatenaCount: HatenaCount): HatenaCountAverage = {
    if (isZero) {
      HatenaCountAverage(0)
    } else {
      HatenaCountAverage(hatenaCount.value / value)
    }
  }

  private def isZero: Boolean = {
    value == 0
  }
}
