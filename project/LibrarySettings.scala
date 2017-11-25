/**
  * 共通ライブラリのビルド定義
  */

import sbt.Keys._

// noinspection TypeAnnotation
object LibrarySettings {
  val Settings = ProjectSettings ++ BuildSettings.Settings ++ StaticAnalysis.Settings ++ ArtifactRepository.Settings

  private lazy val ProjectSettings = Seq(
    /**
      * Organization 定義
      */
    organization := "library"
  )
}
