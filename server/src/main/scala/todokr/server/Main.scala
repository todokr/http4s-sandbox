package todokr.server

import cats.effect.{IO, IOApp}
import cats.effect.ExitCode

object Main extends IOApp:
  def run(args: List[String]): IO[ExitCode] = Server.run()
