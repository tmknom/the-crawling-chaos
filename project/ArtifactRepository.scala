/**
  * Artifact Repository の設定
  */

import sbt.Keys._
import sbt._

// noinspection TypeAnnotation
object ArtifactRepository {

  val Settings = Seq(
    /**
      * publish 先の S3 バケットの指定
      *
      * @see https://blog.dakatsuka.jp/2015/05/16/sbt-publish-to-s3.html
      */
    publishTo := Some("Private Artifact Repository" at repositoryPath)
  )

  private def repositoryPath = {
    sys.env.get("ARTIFACT_REPOSITORY") match {
      case Some(value) => value
      case None => "環境変数に ARTIFACT_REPOSITORY がセットされていません"
    }
  }
}
