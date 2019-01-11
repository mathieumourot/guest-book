package io.q2

import cats.effect._
import cats.data.EitherT
import cats.implicits._
import org.http4s._
import org.http4s.syntax._
import org.http4s.dsl.io._
import org.http4s.server.blaze._
import scala.concurrent.duration.Duration
import org.http4s.server.middleware.{CORS, CORSConfig}
import io.q2.utils.Globals._
import io.q2.api.MiddleWear._ 
import io.q2.repo.GuestBookLog
import GuestBookLog._
import io.q2.api._

object Bootstrap extends IOApp {

  type F[A] = EitherT[IO, Throwable, A]
  val F = implicitly[ConcurrentEffect[F]]

  def run(args: List[String]): IO[ExitCode] =
    ServerStream.stream[IO].compile.drain.as(ExitCode.Success)

  object ServerStream {
    val apiVersion = appCfg.getString("api.version")
    def statusApi[F[_]: Effect] = new io.q2.api.Status[F].routes
    def logApi[F[_]: Effect] = new LogApi[F](new GuestBookLog).routes

    def stream[F[_]: ConcurrentEffect] =
      BlazeBuilder[F]
        .withIdleTimeout(Duration.Inf)
        .bindHttp(appCfg.getInt("api.http.port"), "0.0.0.0")
        .mountService(corsHeader(statusApi),"")
        .mountService(corsHeader(logApi), s"/$apiVersion")
        .serve
    
  }
}
