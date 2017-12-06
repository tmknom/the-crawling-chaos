package library.test.db.internal

import org.scalatest.{MustMatchers, OptionValues}

/**
  * Spec 定義時に必ず extends すべきトレイトたち
  *
  * [[org.scalatestplus.play.PlaySpec]] を参考に選出している。
  *
  * 独自のテスト基底クラスを実装する場合、本トレイトを extends しておくと、
  * PlaySpec で普段書いているのと同じように、テストを書くことができるようになる。
  */
private[test] trait BasicSpecAggregation extends MustMatchers with OptionValues
