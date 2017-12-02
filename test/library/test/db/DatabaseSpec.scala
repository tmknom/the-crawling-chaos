package library.test.db

import library.test.db.internal.PreparationDatabaseSpec
import org.scalatest.{fixture, MustMatchers}
import org.scalatestplus.play.guice.GuiceOneAppPerSuite

/**
  * データベーステスト基底トレイト
  */
trait DatabaseSpec extends fixture.WordSpec with MustMatchers with GuiceOneAppPerSuite with PreparationDatabaseSpec
