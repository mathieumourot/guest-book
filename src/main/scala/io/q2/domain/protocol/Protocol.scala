package io.q2.domain.protocol

import io.circe.{ Decoder, Encoder, HCursor, Json }
import io.q2.domain.Domain._
import io.circe._, io.circe.generic.semiauto._

object Protocol {

  implicit val encodeGuestBook: Encoder[GuestBook] = deriveEncoder
  implicit val decodeGuestBook: Decoder[GuestBook] = deriveDecoder

  implicit val encodeGuestBookState: Encoder[GuestBookState] =
    new Encoder[GuestBookState] {
      final def apply(g: GuestBookState): Json = Json.obj(
        ("name", Json.fromString(g.guestBook.name)),
        ("message", Json.fromString(g.guestBook.message)),
        ("timestamp", Json.fromString(g.timestamp))
      )
    }
  implicit val decodeGuestBookState: Decoder[GuestBookState] =
    new Decoder[GuestBookState]{
      final def apply(c: HCursor): Decoder.Result[GuestBookState] =
        for {
          n ← c.downField("name").as[String]
          m ← c.downField("message").as[String]
          t ← c.downField("timestamp").as[String]
        } yield (
          GuestBookState(
            guestBook=GuestBook(name=n, message=m),
            timestamp=t)
        )
    }
}
