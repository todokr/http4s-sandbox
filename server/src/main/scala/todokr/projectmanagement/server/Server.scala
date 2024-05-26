package todokr.projectmanagement.server

import cats.data.Kleisli
import cats.effect.Async
import cats.effect.ExitCode
import cats.effect.IO
import cats.effect.kernel.Resource
import cats.syntax.all.*
import cats.syntax.compose
import com.comcast.ip4s.*
import fs2.io.net.Network
import org.http4s.HttpRoutes
import org.http4s.dsl.io.*
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.implicits.*
import org.http4s.server.middleware.Logger
import org.typelevel.log4cats.LoggerFactory
import org.typelevel.log4cats.slf4j.Slf4jFactory
import smithy4s.http4s.SimpleRestJsonBuilder
import todokr.projectmanagement.api.healthcheck.HealthCheckService
import todokr.projectmanagement.api.project.ProjectService
import todokr.projectmanagement.server.healthcheck.HealthCheckServiceImpl
import todokr.projectmanagement.server.project.ProjectServiceImpl
import cats.effect.IOApp

object Server:
  given loggerFactory: LoggerFactory[IO] = Slf4jFactory.create[IO]

  def start() =
    val swagger = smithy4s.http4s.swagger.docs[IO](HealthCheckService, ProjectService)
    val routes: Resource[IO, HttpRoutes[IO]] = for
      project <- SimpleRestJsonBuilder.routes(ProjectServiceImpl()).resource
      health <- SimpleRestJsonBuilder.routes(HealthCheckServiceImpl()).resource
    yield (project <+> health <+> swagger)

    routes
      .flatMap: route =>
        EmberServerBuilder
          .default[IO]
          .withHost(ipv4"0.0.0.0")
          .withPort(port"8000")
          .withHttpApp(route.orNotFound)
          .build
      .use(_ => IO.never)
