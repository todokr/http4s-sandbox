$version: "2"

namespace todokr.projectmanagement.api.healthcheck

use alloy#simpleRestJson

@simpleRestJson
service HealthCheckService {
    version: "1.0.0"
    operations: [HealthCheck]
}

@http(method: "GET", uri: "/health", code: 200)
@readonly
operation HealthCheck {
    output: HealthCheckOutput
}

structure HealthCheckOutput {
    @required
    ok: Boolean
    @required
    elapsedMills: Integer
}
