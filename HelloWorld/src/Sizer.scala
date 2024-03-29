import scala.io._
import scala.actors._
import Actor._

object PageLoader {
  def getPageSize(url: String) = Source.fromURL(url).mkString.length
}

val urls = List("http://www.amazon.com",
  "http://www.twitter.com",
  "http://www.google.com",
  "http://www.cnn.com")

def timeMethod(method: () => Unit) = {
  val start = System.nanoTime()
  method()
  val end = System.nanoTime()
  println("Method took " + (end - start) / 1000000000.0 + " seconds.")
}

def getPagesSequentially() = {
  urls.foreach{ url =>
    println("Size for " + url + ": " + PageLoader.getPageSize(url))
  }
}

def getPageConcurrently() = {
  val caller = self

  urls.foreach{ url => actor { caller ! (url, PageLoader.getPageSize(url)) } }

  for (i <- 1 to urls.size) {
    receive { case (url, size) => println("Size for " + url + ": " + size) }
  }
}

println("Sequential run:")
timeMethod(getPagesSequentially)

println("Concurrent run:")
timeMethod(getPageConcurrently)