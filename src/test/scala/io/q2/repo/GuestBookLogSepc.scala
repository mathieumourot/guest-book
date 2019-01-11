package io.q2.repo

import io.circe._
import io.circe.parser._
import io.circe.generic.auto._
import io.circe.syntax._
import io.q2.domain.Domain._
import org.specs2._

class GuestBookLogSpec extends Specification { def is = s2"""

    GuesBookLog repository specs
      safe appender removes oldest guess-book log entry to store new elements  $e1
      roundtrip JSON protocol transformation of eGeussBook $e2
      posting guessbook changes the guesbookloga $e3
 """

  import GuestBookLog._

  def e1 = {
    var computed: Vector[Int]=Vector()
    val guestBookLog = new GuestBookLog()
    (0 to 12).foreach(x ⇒ 
      computed=guestBookLog.safeAppender(3)(computed, x))
    computed === (0 to 12).drop(13-3)

  }
  def e2 =  {
    val acutal = """
 {
  "name": "John Doe",
  "message": "Hello!"
}
"""
    val computed = for {
      j ← parse(acutal)
      g ← j.as[GuestBook]
    } yield(g)
    (  computed.isRight &&
      computed.toOption.get == GuestBook(
        name="John Doe",
        message="Hello!") ) === true
  }
  def e3 =  {
    val guestBookLog = new GuestBookLog()
    val guestBook=
      """{"name": "John Doe", "message": "Hello!"}"""
    guestBookLog.log(guestBook)
    guestBookLog.log(guestBook)
  val computed = guestBookLog.log
    (
      computed.size == 2 &&
        computed(0).name == computed(1).name &&
        computed(0).timestamp != computed(1).timestamp
    ) === true

  }
}
