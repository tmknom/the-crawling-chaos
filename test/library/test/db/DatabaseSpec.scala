package library.test.db

import library.test.db.internal.{BasicSpecAggregation, PreparationDatabaseSpec}
import org.scalatest.fixture
import org.scalatestplus.play.guice.GuiceOneAppPerSuite

/**
  * データベーステスト基底トレイト
  */
trait DatabaseSpec extends fixture.WordSpec with BasicSpecAggregation with GuiceOneAppPerSuite with PreparationDatabaseSpec
