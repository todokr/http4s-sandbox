$version: "2"

namespace io.github.todokr.projectmanagement.api

use alloy#simpleRestJson
use smithy4s.meta#adt

@simpleRestJson
service HealthCheckService {
    version: "1.0.0"
    operations: [Ping, SomeOps]
}

@http(method: "GET", uri: "/ping", code: 200)
@readonly
operation Ping {
    output: Health
}

structure Health {
    @required
    ok: Boolean
}

@http(method: "POST", uri: "/someops", code: 200)
operation SomeOps {
    input: SomeOpsInput
    output: SomeOpsOutput
}

structure SomeOpsInput {
    @required
    name: String
}

structure SomeOpsOutput {
    @required
    id: String
    action: SomeOpsAction
}

@adt
union SomeOpsAction {
    create: CreatedOpsOutput
    update: UpdatedOpsOutput
    delete: DeletedOpsOutput
}

structure CreatedOpsOutput {
    @required
    id: String
    createdName: String
}

structure UpdatedOpsOutput {
    @required
    id: String
    updatedCount: Integer
}

structure DeletedOpsOutput {
    @required
    id: String
    deletedTime: Timestamp
}
