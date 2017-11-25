// The Play plugin
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.6.7")

/**
  * マイグレーションツール
  *
  * flyway-play(https://github.com/flyway/flyway-play)というのもあるが
  * evolution同様に単発コマンドとして起動できないので
  * 単発コマンドとして起動できるflyway-sbtを使う。
  *
  * @see https://flywaydb.org/documentation/sbt/
  */
addSbtPlugin("org.flywaydb" % "flyway-sbt" % "4.2.0")
resolvers += "Flyway" at "https://flywaydb.org/repo"

/**
  * 自動コードフォーマット
  *
  * @see https://github.com/lucidsoftware/neo-sbt-scalafmt
  */
addSbtPlugin("com.lucidchart" % "sbt-scalafmt" % "1.14")

/**
  * カバレッジ
  *
  * @see https://github.com/scoverage/sbt-scoverage
  */
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.5.0")

/**
  * がバレッジ＆静的解析：Codacy
  *
  * 事前準備として Codacy 上で発行したトークンを CirclecCI の Environment Variables に
  * CODACY_PROJECT_TOKEN という名前で保存しておく必要がある。
  *
  * @see https://support.codacy.com/hc/en-us/articles/207279819-Coverage
  * @see https://github.com/codacy/sbt-codacy-coverage
  */
addSbtPlugin("com.codacy" % "sbt-codacy-coverage" % "1.3.8")

/**
  * 静的解析ツール：Scalastyle
  *
  * @see http://www.scalastyle.org/sbt.html
  */
addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "0.8.0")

/**
  * 静的解析ツール：WartRemover
  *
  * @see http://www.wartremover.org/
  */
addSbtPlugin("org.wartremover" % "sbt-wartremover" % "2.2.1")

/**
  * コピペチェック
  *
  * @see https://github.com/sbt/cpd4sbt
  */
addSbtPlugin("de.johoop" % "cpd4sbt" % "1.2.0")

/**
  * 依存ライブラリのアップデート確認
  *
  * @see https://github.com/rtimush/sbt-updates
  */
addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.3.1")

/**
  * プロジェクトの統計情報取得
  */
addSbtPlugin("com.orrsella" % "sbt-stats" % "1.0.5")

/**
  * S3 をプライベートな Maven リポジトリとして扱う
  *
  * @see https://github.com/frugalmechanic/fm-sbt-s3-resolver
  */
addSbtPlugin("com.frugalmechanic" % "fm-sbt-s3-resolver" % "0.12.0")

/**
  * sbt 実行時のエラーメッセージや警告メッセージを見やすく整形
  *
  * @see https://github.com/Duhemm/sbt-errors-summary
  */
addSbtPlugin("org.duhemm" % "sbt-errors-summary" % "0.6.0")
