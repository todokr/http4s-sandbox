$version: "2"

namespace todokr.projectmanagement.api.project

use alloy#simpleRestJson
use alloy#uuidFormat
use smithy4s.meta#adt

string AccountToken

@uuidFormat
string ProjectId

@error("client")
@httpError(400)
structure ValidationError {
    @required
    message: String
}

@error("client")
@httpError(401)
structure UnauthorizedError {
    message: String
}

@error("client")
@httpError(403)
structure ForbiddenError {}

@simpleRestJson
service ProjectService {
    version: "1.0.0"
    operations: [
        ListProjects
        GetProject
        CreateProject
        UpdateProject
        DeleteProject
    ]
}

structure Project {
    @required
    id: String
    @required
    name: String
    description: String
}

list Projects {
    member: Project
}

@http(method: "GET", uri: "/projects", code: 200)
@readonly
operation ListProjects {
    input: ListProjectsInput
    output: ListProjectsOutput
    errors: [UnauthorizedError]
}

structure ListProjectsInput {
    @httpHeader("Cookie")
    @required
    token: AccountToken
}

structure ListProjectsOutput {
    @required
    projects: Projects
}

@http(method: "GET", uri: "/projects/{projectId}", code: 200)
@readonly
operation GetProject {
    input: GetProjectInput
    output: GetProjectOutput
    errors: [UnauthorizedError, ForbiddenError]
}

structure GetProjectInput {
    @httpHeader("Cookie")
    @required
    token: AccountToken
    @httpLabel
    @required
    projectId: ProjectId
}

structure GetProjectOutput {
    project: Project
}

@http(method: "POST", uri: "/projects", code: 201)
operation CreateProject {
    input: CreateProjectInput
    output: CreateProjectOutput
    errors: [
        UnauthorizedError
        ForbiddenError
        ValidationError
    ]
}

structure CreateProjectInput {
    @httpHeader("Cookie")
    @required
    token: AccountToken
    @required
    name: String
    description: String
}

structure CreateProjectOutput {
    @required
    project: Project
}

@http(method: "PUT", uri: "/projects/{projectId}", code: 200)
@idempotent
operation UpdateProject {
    input: UpdateProjectInput
    output: UpdateProjectOutput
    errors: [
        UnauthorizedError
        ForbiddenError
        ValidationError
    ]
}

structure UpdateProjectInput {
    @httpHeader("Cookie")
    @required
    token: AccountToken
    @httpLabel
    @required
    projectId: ProjectId
    @required
    name: String
    description: String
}

structure UpdateProjectOutput {
    @required
    project: Project
}

@http(method: "DELETE", uri: "/projects/{projectId}", code: 204)
@idempotent
operation DeleteProject {
    input: DeleteProjectInput
    errors: [UnauthorizedError, ForbiddenError]
}

structure DeleteProjectInput {
    @httpHeader("Cookie")
    @required
    token: AccountToken
    @httpLabel
    @required
    projectId: ProjectId
}
