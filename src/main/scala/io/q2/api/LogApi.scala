package io.q2.api

import org.http4s.HttpRoutes
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl
import io.circe.syntax._
import io.circe.Json
import cats.effect._
import io.q2.repo._
import GuestBookLog._
import com.typesafe.scalalogging.Logger
import io.q2.domain.Domain._
import io.q2.domain.protocol.Protocol._
import org.http4s._

class LogApi[F[_]: Sync](guestBookLog: GuestBookLog) extends Http4sDsl[F] {
  val log = Logger("LogApi")

  val routes: HttpRoutes[F] =
    HttpRoutes.of[F] {
      case req @ GET -> Root / "log" =>
        val v = guestBookLog.log
        v.headOption match {
          case Some(r) ⇒ Ok(Json.fromValues(v.map( x ⇒ x.asJson) ))
          case None ⇒ NotFound("no logs were found!")
        }
      case req @ POST -> Root / "log" =>
        req.decode[String] { m =>
          guestBookLog.log(m) match {
            case Right(r) ⇒ Ok(r.asJson)
            case Left(e) if e.code == ErrorCode.invalidData ⇒
              log.error(s"$e")
              BadRequest(s"request $m isInvalid!" )
            case Left(e) if e.code == ErrorCode.guestOverflow ⇒
              log.error(s"$e")
              InternalServerError(e.toString)
          }
        }
    }
}
