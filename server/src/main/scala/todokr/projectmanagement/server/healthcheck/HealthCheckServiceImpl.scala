package todokr.projectmanagement.server.healthcheck

import todokr.projectmanagement.api.healthcheck.*
import cats.effect.IO

class HealthCheckServiceImpl extends HealthCheckService[IO]:
  override def healthCheck(): IO[HealthCheckOutput] =
    IO.pure(HealthCheckOutput(ok = true, elapsedMills = 123))
