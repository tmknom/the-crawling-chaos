/**
  * Scala/Javaの設定
  */

import sbt.Keys._
import sbt._

// noinspection TypeAnnotation
object BuildSettings {
  val Settings = Seq(
    /**
      * Scalaのバージョン
      */
    scalaVersion := "2.12.4",

    /**
      * resolvers の設定
      *
      * 共通ライブラリを取得するため、プライベートな Artifact Repository を追加
      */
    resolvers += "Private Artifact Repository" at repositoryPath,

    /**
      * システムのタイムゾーンを指定
      *
      * scalikejdbc などの一部のライブラリでは、システムのタイムゾーンがそのまま使われるため
      * JVM のタイムゾーンは明示的に指定しておく。
      *
      * 本番環境で動かすときには sbt 経由でアプリケーションを起動するわけではないので
      * 起動オプションに明示的に指定する必要があることに注意。
      */
    javaOptions += "-Duser.timezone=Asia/Tokyo",

    /**
      * Scala コンパイラに警告を色々出してもらう設定
      *
      * -Ywarn-unused で imports と patvars を除外している理由は routes が警告を出すためである。
      * 本当は有効にしたいのだが routes だけ除外するという設定ができなかったため、泣く泣くコメントアウトした。
      *
      * -Ywarn-unused-import をコメントアウトしている理由も -Ywarn-unused 同様である。
      *
      * -Xfatal-warning であるが、有効にするとなぜか共通ライブラリがエラーになる(警告ないのに。。)のでコメントアウトしている。
      * 共通ライブラリを別リポジトリに切り出したら、有効にしても良いかもしれない。
      *
      * @see http://qiita.com/kawachi/items/1c1d063de91c5445e8bc
      */
    scalacOptions ++= Seq(
      "-deprecation", // @deprecated な API を使用している
      "-feature", // 実験的な機能や注意すべき機能を使用している
      "-unchecked", // 型消去などでパターンマッチが有効に機能しない
      "-Xlint", // Enable or disable specific warnings: `_' for all, `-Xlint:help' to list choices.
      "-Ywarn-dead-code", // Warn when dead code is identified.
      "-Ywarn-numeric-widen", // Warn when numerics are widened.
      "-Ywarn-unused:-patvars,-imports,_", // Warn when local and private vals, vars, defs, and types are unused.
      // "-Ywarn-unused-import", // Warn when imports are unused.
      "-Ywarn-value-discard" // Warn when non-Unit expression results are unused.
      //, "-Xfatal-warning" // 警告があった場合、コンパイルエラーにする
    ),

    /**
      * logback でログをファイル出力するか：conf/logback.xmlで参照する値
      *
      * 値がセットされていると、ログがファイルに出力される。
      * sbt 経由でアプリケーションを起動した場合のみ、ログをファイル出力する。
      */
    javaOptions += "-Dlogback.appender.file=true",

    /**
      * logback の application のログレベル：conf/logback.xmlで参照する値
      *
      * ローカル環境では DEBUG レベルのほうが便利なので、明示的に指定している。
      * 本番環境などでは conf/logback.xml で記述されたデフォルト値である INFO レベルで出力する。
      */
    javaOptions += "-Dlogback.loglevel.application=DEBUG",

    /**
      * logback の scalikejdbc のログレベル：conf/logback.xmlで参照する値
      *
      * ローカル環境では DEBUG レベルのほうが便利なので、明示的に指定している。
      * 本番環境などでは conf/logback.xml で記述されたデフォルト値である INFO レベルで出力する。
      */
    javaOptions += "-Dlogback.loglevel.scalikejdbc=DEBUG",

    /**
      * テスト時の設定ファイルの切り替え
      */
    javaOptions in Test += "-Dconfig.file=conf/test.conf",

    /**
      * テスト時の logback 設定ファイルの切り替え
      */
    javaOptions in Test += "-Dlogger.resource=logback-test.xml",

    /**
      * テスト時に JVM を fork する
      *
      * play のデフォルトでは true だが、サブプロジェクトはなぜか false になってしまう。
      * そのため、「javaOptions will be ignored, fork is set to false」という警告が出てしまっていた。
      *
      * root プロジェクトはデフォルトで true の設定なので問題ないとは思うが、
      * 共通ライブラリを別リポジトリに切り出した後であれば、この設定は削除してしまって構わない。
      */
    fork in Test := true,

    /**
      * ScalaTest のオプション設定
      *
      * -oD : テストケースごとに実行時間の表示
      * -eI : 失敗したテストを最後にまとめて表示
      *
      * @see http://www.scalatest.org/user_guide/using_scalatest_with_sbt
      */
    testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-oD", "-eI")
  )

  private def repositoryPath = {
    sys.env.get("ARTIFACT_REPOSITORY") match {
      case Some(value) => value
      case None => "s3://example.com" // S3 のパス形式にしておかないと IntelliJ が最初立ち上がらない
    }
  }
}
