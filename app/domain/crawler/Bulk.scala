package domain.crawler

import play.api.Logger

import scala.collection.mutable

trait Bulk {
  @SuppressWarnings(Array("org.wartremover.warts.MutableDataStructures"))
  val errors: mutable.ListBuffer[String] = mutable.ListBuffer.empty[String]

  def withLoop[I](items: List[I])(f: (I, String) => Unit): Unit = {
    val itemsCount = items.size
    items.zipWithIndex.foreach {
      case (item, index) =>
        val progress = Progress.calculate(index, itemsCount)
        f(item, progress)
    }
    Logger.warn(s"${this.getClass.getSimpleName} error ${errors.size.toString} items : ${errors.toString()}")
  }
}
