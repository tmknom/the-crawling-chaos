/**
  * デプロイ時の設定
  */

import com.typesafe.sbt.packager.universal.UniversalPlugin.autoImport.Universal
import sbt.Keys._

// noinspection TypeAnnotation
object Deploy {

  // dist タスクで除外する設定ファイル名
  private val ExcludedResources = Seq(
    "logback-test.xml",
    "test.conf"
  )

  val Settings = Seq(
    /**
      * dist タスク実行時に、テスト用の設定ファイルを除外する
      *
      * @see https://stackoverflow.com/questions/24375624/how-to-exclude-files-under-conf-folder-for-distribution
      * @see https://github.com/EHRI/ehri-frontend/blob/fd888d17a75dcf6fa974237d4f21e2e253056307/build.sbt
      */
    mappings in Universal := (mappings in Universal).value.filterNot { case (f, _) =>
      ExcludedResources contains f.getName
    }
  )
}
