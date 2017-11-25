/**
  * 独自実装した共通ライブラリの依存定義
  */

import sbt._

// noinspection TypeAnnotation
object PrivateLibrary {
  lazy val LibraryOrganization = "library"
  lazy val LibraryVersion = "0.1.0"

  val Core = LibraryOrganization %% "library-core" % LibraryVersion
  val Datetime = LibraryOrganization %% "library-datetime" % LibraryVersion
  val Spray = LibraryOrganization %% "library-spray" % LibraryVersion
  val Controller = LibraryOrganization %% "library-controller" % LibraryVersion
  val Validation = LibraryOrganization %% "library-validation" % LibraryVersion
  val Flyway = LibraryOrganization %% "library-flyway" % LibraryVersion
  val Migration = LibraryOrganization %% "library-migration" % LibraryVersion
  val Filter = LibraryOrganization %% "library-filter" % LibraryVersion
  val Errorhandler = LibraryOrganization %% "library-errorhandler" % LibraryVersion
  val Test = LibraryOrganization %% "library-test" % LibraryVersion

  val Dependencies = Seq(
    Core,
    Datetime,
    Spray,
    Controller,
    Validation,
    Flyway,
    Migration,
    Filter,
    Errorhandler,
    Test
  )
}
