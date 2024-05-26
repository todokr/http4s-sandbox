package todokr.projectmanagement.server.project

import cats.effect.IO
import cats.syntax.option.*
import todokr.projectmanagement.api.project.*
import java.util.UUID

class ProjectServiceImpl extends ProjectService[IO]:

  override def listProjects(token: AccountToken): IO[ListProjectsOutput] =
    IO.pure(
      ListProjectsOutput(projects =
        List(
          Project(
            id = UUID.randomUUID().toString(),
            name = "Project 1",
            description = Some("Project 1 description")
          ),
          Project(
            id = UUID.randomUUID().toString(),
            name = "Project 2",
            description = Some("Project 2 description")
          )
        )
      )
    )

  override def createProject(token: AccountToken, name: String, description: Option[String]): IO[CreateProjectOutput] =
    IO.pure(
      CreateProjectOutput(
        project = Project(
          id = UUID.randomUUID().toString(),
          name = name,
          description = description
        )
      )
    )

  override def getProject(token: AccountToken, projectId: ProjectId): IO[GetProjectOutput] =
    IO.pure(
      GetProjectOutput(
        project = Project(
          id = UUID.randomUUID().toString(),
          name = "Project 1",
          description = Some("Project 1 description")
        ).some
      )
    )

  override def updateProject(
      token: AccountToken,
      projectId: ProjectId,
      name: String,
      description: Option[String]
  ): IO[UpdateProjectOutput] =
    IO.pure(
      UpdateProjectOutput(
        project = Project(
          id = UUID.randomUUID().toString(),
          name = name,
          description = description
        )
      )
    )

  override def deleteProject(token: AccountToken, projectId: ProjectId): IO[Unit] =
    IO.unit
