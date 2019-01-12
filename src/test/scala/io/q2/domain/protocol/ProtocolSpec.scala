package io.q2.repo

import io.circe._
import io.circe.parser._
import io.circe.syntax._
import io.q2.domain.Domain._
import io.q2.domain.protocol.Protocol._
import org.specs2._

class ProtocolSpec extends Specification { def is = s2"""

    Domain model Json protocol specs
      GuesBook Json protocol $e1
      GuesBookLog Json protocol $e2
 """
  def e1 = {
    val actual = GuestBookState(GuestBook("Barack Obama", "PODS was here!"))
    val computed = actual.asJson
    println(s"JSON CONVERSION ${computed}")
    actual === computed.as[GuestBookState].toOption.get
  }
  def e2 = {
    val actual = GuestBook("Barack Obama", "PODS was here!")
    val computed = actual.asJson
    println(s"JSON CONVERSION ${computed}")
    actual === computed.as[GuestBook].toOption.get
  }
   
}
