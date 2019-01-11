package io.q2.api

import cats.effect._
import cats.implicits._
import org.http4s._
import scala.concurrent.duration._
import org.http4s.server.middleware.{CORS, CORSConfig}
import org.http4s.util.CaseInsensitiveString


object MiddleWear {

  val methodConfig = CORSConfig(
    anyOrigin = true,
    anyMethod = false,
    allowedMethods = Set("GET", "POST", "HEAD", "OPTIONS").some,
    allowedHeaders = Some(
      Set("Origin",
          "X-Requested-With",
          "Content-Type",
          "Accept",
          "Authorization")),
    allowCredentials = true,
    allowedOrigins = Set("*"),
    maxAge = 1.day.toSeconds
  )

  def addHeader[F[_]: Effect](resp: Response[F], header: Header) =
    resp match {
      case Status.Successful(resp) => resp.putHeaders(header)
      case resp => resp
    }
  def corsHeader[F[_]: Effect](service: HttpRoutes[F]) = {
    CORS(service, methodConfig).map(
      addHeader(_, Header("Server", "io.q2.guest-book"))) 
  }

}
