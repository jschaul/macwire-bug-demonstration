package com.example

import com.softwaremill.macwire.MacwireMacros._

trait Service {
  def hello = println("Hello, world!")
}

object MyService extends Service

trait MacwireRegistry {

  /**
  * some comment what thus service does.
  */
  lazy val service: Service = MyService

  lazy val hello: Hello = wire[Hello]
}


class Hello(service: Service) {
  def main(args: Array[String]): Unit = {
     service.hello
  }
}
