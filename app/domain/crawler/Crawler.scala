package domain.crawler

import domain.Identifier

trait Crawler extends Bulk with QuietlyExecution {
  def withSleepLoop[I <: Identifier[String]](items: List[I])(f: (I) => Unit): Unit = {
    withLoop[I](items) { (item, progress) =>
      withSleep[String](item, progress, errors) { (_) =>
        f(item)
      }
    }
  }
}
