package infrastructure.qiita.article.json

import javax.inject.{Inject, Singleton}

import domain.qiita.article.QiitaArticleUrl
import domain.qiita.article.contribution.{PocketCount, PocketGateway}
import library.scalaj.ScalajHttpAdaptor

@Singleton
final class HttpPocketGateway @Inject()(scalajHttpAdaptor: ScalajHttpAdaptor) extends PocketGateway {
  override def fetch(qiitaArticleUrl: QiitaArticleUrl): PocketCount = {
    val response = scalajHttpAdaptor.get(qiitaArticleUrl.pocketApiUrl)
    PocketParser(response).parse
  }
}
