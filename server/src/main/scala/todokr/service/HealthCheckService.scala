package todokr.service
import io.github.todokr.projectmanagement.api.*

import cats.effect.IO
import smithy4s.Timestamp

class HealthCheckServiceImpl extends HealthCheckService[IO]:
  override def ping(): IO[Health] = IO.pure(Health(ok = true))
  override def someOps(name: String): IO[SomeOpsOutput] = IO.pure:
    val action: SomeOpsAction = name match
      case "create" => SomeOpsAction.create(CreatedOpsOutput(name, Some("created")))
      case "update" => SomeOpsAction.update(UpdatedOpsOutput(name, Some(12)))
      case "delete" => SomeOpsAction.delete(DeletedOpsOutput(name, Some(Timestamp.nowUTC())))
    SomeOpsOutput(name, Some(action))
