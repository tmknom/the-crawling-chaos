package infrastructure.qiita.article.json

import javax.inject.{Inject, Singleton}

import domain.qiita.article.QiitaArticleUrl
import domain.qiita.article.contribution.{HatenaCount, HatenaGateway}
import library.scalaj.ScalajHttpAdaptor

@Singleton
final class HttpHatenaGateway @Inject()(scalajHttpAdaptor: ScalajHttpAdaptor) extends HatenaGateway {
  override def fetch(qiitaArticleUrl: QiitaArticleUrl): HatenaCount = {
    val response = scalajHttpAdaptor.get(qiitaArticleUrl.hatenaApiUrl)
    response match {
      case "" => HatenaCount(0)
      case v  => HatenaCount(v.toInt)
    }
  }
}
