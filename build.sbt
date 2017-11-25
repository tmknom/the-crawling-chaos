name := """play-starter"""

version := "1.0-SNAPSHOT"

/**
  * root プロジェクトのビルド定義
  */
lazy val root = (project in file("."))
  .settings(BuildSettings.Settings)
  .settings(Deploy.Settings)
  .settings(Migration.Settings)
  .settings(CodeFormat.Settings)
  .settings(Coverage.Settings)
  .settings(StaticAnalysis.Settings)
  .settings(StaticAnalysis.PlaySettings)
  .settings(libraryDependencies ++= Dependencies.Application)
  .enablePlugins(PlayScala)
  .enablePlugins(CopyPasteDetector)

resolvers += Resolver.sonatypeRepo("snapshots")

/**
  * 共通ライブラリのビルド定義
  */

import Library._

lazy val library = (project in file("library"))
  .settings(BuildSettings.Settings)
  .aggregate(core)
  .dependsOn(core)
  .aggregate(datetime)
  .dependsOn(datetime)
  .aggregate(spray)
  .dependsOn(spray)
  .aggregate(controller)
  .dependsOn(controller)
  .aggregate(validation)
  .dependsOn(validation)
  .aggregate(task)
  .dependsOn(task)
  .aggregate(flyway)
  .dependsOn(flyway)
  .aggregate(migration)
  .dependsOn(migration)
  .aggregate(filter)
  .dependsOn(filter)
  .aggregate(errorhandler)
  .dependsOn(errorhandler)
  .aggregate(test)
  .dependsOn(test)
  .settings(publish := {}) // library プロジェクトそのものは publish しないので無効化しておく

lazy val core = (project in file("library/core"))
  .settings(name := "library-core")
  .settings(version := "0.2.0-SNAPSHOT")
  .settings(LibrarySettings.Settings)
  .settings(libraryDependencies ++= Seq(PlayFramework, ScalatestplusPlay))

lazy val datetime = (project in file("library/datetime"))
  .settings(name := "library-datetime")
  .settings(version := "0.2.0-SNAPSHOT")
  .settings(LibrarySettings.Settings)
  .settings(libraryDependencies ++= Seq(ScalatestplusPlay))

lazy val task = (project in file("library/task"))
  .settings(name := "library-task")
  .settings(version := "0.2.0-SNAPSHOT")
  .settings(LibrarySettings.Settings)
  .settings(libraryDependencies ++= Seq(PlayFramework, ScalatestplusPlay))

lazy val spray = (project in file("library/spray"))
  .settings(name := "library-spray")
  .settings(version := "0.2.0-SNAPSHOT")
  .settings(LibrarySettings.Settings)
  .settings(libraryDependencies ++= Seq(SprayJson, ScalatestplusPlay))

lazy val controller = (project in file("library/controller"))
  .settings(name := "library-controller")
  .settings(version := "0.2.0-SNAPSHOT")
  .settings(LibrarySettings.Settings)
  .settings(libraryDependencies ++= Seq(PlayFramework, SprayJson, ScalatestplusPlay))

lazy val validation = (project in file("library/validation"))
  .settings(name := "library-validation")
  .settings(version := "0.2.0-SNAPSHOT")
  .settings(LibrarySettings.Settings)
  .dependsOn(core)
  .settings(libraryDependencies ++= Seq(PlayFramework, ScalatestplusPlay))

lazy val flyway = (project in file("library/flyway"))
  .settings(name := "library-flyway")
  .settings(version := "0.2.0-SNAPSHOT")
  .settings(LibrarySettings.Settings)
  .settings(
    libraryDependencies ++= Seq(PlayFramework,
      PlayJdbcApi,
      FlywayCore,
      ScalatestplusPlay,
      jdbc % Test,
      MysqlConnectorJava % Test))

lazy val migration = (project in file("library/migration"))
  .settings(name := "library-migration")
  .settings(version := "0.2.0-SNAPSHOT")
  .settings(LibrarySettings.Settings)
  .dependsOn(task, flyway)
  .settings(libraryDependencies ++= Seq(ScalatestplusPlay))

lazy val filter = (project in file("library/filter"))
  .settings(name := "library-filter")
  .settings(version := "0.2.0-SNAPSHOT")
  .settings(LibrarySettings.Settings)
  .dependsOn(core)
  .settings(libraryDependencies ++= Seq(PlayFramework, LogstashLogbackEncoder, ScalatestplusPlay))

lazy val errorhandler = (project in file("library/errorhandler"))
  .settings(name := "library-errorhandler")
  .settings(version := "0.2.0-SNAPSHOT")
  .settings(LibrarySettings.Settings)
  .dependsOn(core)
  .settings(libraryDependencies ++= Seq(PlayFramework, LogstashLogbackEncoder, ScalatestplusPlay))

lazy val test = (project in file("library/test"))
  .settings(name := "library-test")
  .settings(version := "0.2.0-SNAPSHOT")
  .settings(LibrarySettings.Settings)
  .dependsOn(flyway)
  .settings(libraryDependencies ++= Seq(ScalatestplusPlayForMain))
