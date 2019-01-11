package io.q2.domain

import java.time.format._
import java.time._

object Domain {
object ErrorCode extends Enumeration {
  type ErrorCode = Value
  val invalidData,
      notFound,
      guestOverflow,
      unknown = Value
}

import ErrorCode._

case class Err(code: ErrorCode, msg: String)

case class GuestBook(
    name: String,
    message: String
)
case class GuestBookState(
  name: String,
  message: String,
  timestamp: String=
    ZonedDateTime.now( ZoneOffset.UTC )
      .format( DateTimeFormatter.ISO_INSTANT )
)
}
