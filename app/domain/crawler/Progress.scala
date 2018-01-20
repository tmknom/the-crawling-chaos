package domain.crawler

object Progress {
  def calculate(index: Int, itemsCount: Int): String = {
    val progress = ((index + 1) / itemsCount.toDouble) * 100.0
    s"(${index + 1} / $itemsCount = $progress%)"
  }
}
