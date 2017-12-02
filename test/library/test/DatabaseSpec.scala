package library.test

import library.test.internal.BasicSpecAggregation
import library.test.internal.db.PreparationDatabaseSpec
import org.scalatest.fixture
import org.scalatestplus.play.guice.GuiceOneAppPerSuite

/**
  * データベーステスト基底トレイト
  */
trait DatabaseSpec extends fixture.WordSpec with BasicSpecAggregation with GuiceOneAppPerSuite with PreparationDatabaseSpec
