package todokr.server

import cats.effect.Async
import cats.effect.IO
import cats.syntax.all.*
import com.comcast.ip4s.*
import fs2.io.net.Network
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.implicits.*
import org.http4s.dsl.io.*
import org.http4s.server.middleware.Logger
import org.http4s.HttpRoutes
import cats.effect.ExitCode
import org.typelevel.log4cats.LoggerFactory
import org.typelevel.log4cats.slf4j.Slf4jFactory
import cats.effect.kernel.Resource
import smithy4s.http4s.SimpleRestJsonBuilder
import todokr.service.HealthCheckServiceImpl
import io.github.todokr.projectmanagement.api.HealthCheckService
import io.github.todokr.projectmanagement.api.HealthCheckServiceOperation.SomeOps

object Server:
  given loggerFactory: LoggerFactory[IO] = Slf4jFactory.create[IO]

  val pingPong = HttpRoutes.of[IO] { case GET -> Root / "ping" =>
    Ok("pong")
  }

  def run() =
    Routes.all
      .flatMap: routes =>
        EmberServerBuilder
          .default[IO]
          .withHost(ipv4"0.0.0.0")
          .withPort(port"8000")
          .withHttpApp(routes.orNotFound)
          .build
      .use(_ => IO.never)

object Routes:
  private val healthCheck: Resource[IO, HttpRoutes[IO]] =
    SimpleRestJsonBuilder.routes(HealthCheckServiceImpl()).resource

  private val docs: HttpRoutes[IO] =
    smithy4s.http4s.swagger.docs[IO](HealthCheckService)

  val all: Resource[IO, HttpRoutes[IO]] = healthCheck.map(_ <+> docs)
