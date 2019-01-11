package io.q2.repo

import io.q2.domain.Domain._
import io.circe._, io.circe.parser._
import io.circe.generic.auto._
import io.circe.syntax._


object GuestBookLog {
//TODO impl Data store for next iteration
  private var state: Vector[GuestBookState]=Vector()

  val  maxSize=1000
}
class GuestBookLog {
  import GuestBookLog._

  def  log: Vector[GuestBookState] =
    state

  def log(item: String): Either[Err, GuestBookState] = {
   val z = for {
      j ← parse(item)
      g ← j.as[GuestBook]
   } yield(g)
     z match {
       case Right(r) ⇒
         val newState= GuestBookState(r.name, r.message)
         state =safeAppenderMaxSize(state)(newState)
          Right(state.last)
      case Left(e) ⇒ 
        Left(Err(ErrorCode.invalidData, s"${e}"))
    }
  }

  def safeAppender[T](max: Int)(v: Vector[T], item: T): Vector[T] = 
    if(v.size >= max)
      v.tail :+ item
    else v :+ item

  /**
   * remove the oldest elements to make room for the new elements
   **/
  val safeAppenderMaxSize: Vector[GuestBookState] ⇒
    GuestBookState ⇒
    Vector[GuestBookState] = 
      v ⇒ i ⇒ safeAppender(maxSize)(v, i)
}
