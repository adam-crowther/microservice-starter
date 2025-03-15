rootProject.name = "ticketing"

include("airline-mdm:container")
include("airline-mdm:domain:core")
include("airline-mdm:domain:application-service")
include("airline-mdm:infrastructure:data-access")
include("airline-mdm:infrastructure:messaging")
include("airline-mdm:presentation:api-spec")
include("airline-mdm:presentation:rest-controller")
include("airline-mdm:presentation:swagger")

findProject(":airline-mdm:container")?.name = "airline-mdm-container"
findProject(":airline-mdm:domain")?.name = "airline-mdm-domain"
findProject(":airline-mdm:domain:core")?.name = "airline-mdm-domain-core"
findProject(":airline-mdm:domain:application-service")?.name = "airline-mdm-application-service"
findProject(":airline-mdm:infrastructure")?.name = "airline-mdm-infrastructure"
findProject(":airline-mdm:infrastructure:data-access")?.name = "airline-mdm-data-access"
findProject(":airline-mdm:infrastructure:messaging")?.name = "airline-mdm-messaging"
findProject(":airline-mdm:presentation")?.name = "airline-mdm-presentation"
findProject(":airline-mdm:presentation:api-spec")?.name = "airline-mdm-api-spec"
findProject(":airline-mdm:presentation:rest-controller")?.name = "airline-mdm-rest-controller"
findProject(":airline-mdm:presentation:swagger")?.name = "airline-mdm-swagger"

include("airline-approval-service:container")
include("airline-approval-service:domain:core")
include("airline-approval-service:domain:application-service")
include("airline-approval-service:infrastructure:data-access")
include("airline-approval-service:infrastructure:messaging")

findProject(":airline-approval-service:container")?.name = "airline-approval-container"
findProject(":airline-approval-service:domain")?.name = "airline-approval-domain"
findProject(":airline-approval-service:domain:core")?.name = "airline-approval-domain-core"
findProject(":airline-approval-service:domain:application-service")?.name = "airline-approval-application-service"
findProject(":airline-approval-service:infrastructure")?.name = "airline-approval-infrastructure"
findProject(":airline-approval-service:infrastructure:data-access")?.name = "airline-approval-data-access"
findProject(":airline-approval-service:infrastructure:messaging")?.name = "airline-approval-messaging"

include("common")
include("common:application")
include("common:container")
include("common:domain")
include("common:helper")
include("common:presentation")
include("common:infrastructure")
include("common:kafka")
include("common:kafka:kafka-libs")
include("common:kafka:kafka-model")
include("common:saga")
include("common:swagger")
include("common:test")

findProject(":common:application")?.name = "common-application"
findProject(":common:container")?.name = "common-container"
findProject(":common:domain")?.name = "common-domain"
findProject(":common:helper")?.name = "common-helper"
findProject(":common:presentation")?.name = "common-presentation"
findProject(":common:infrastructure")?.name = "common-infrastructure"
findProject(":common:kafka")?.name = "common-kafka"
findProject(":common:kafka:kafka-libs")?.name = "kafka-libs"
findProject(":common:kafka:kafka-model")?.name = "kafka-model"
findProject(":common:saga")?.name = "common-saga"
findProject(":common:swagger")?.name = "common-swagger"
findProject(":common:test")?.name = "common-test"

include("customer-mdm:container")
include("customer-mdm:domain:core")
include("customer-mdm:domain:application-service")
include("customer-mdm:infrastructure:data-access")
include("customer-mdm:infrastructure:messaging")
include("customer-mdm:presentation:api-spec")
include("customer-mdm:presentation:rest-controller")
include("customer-mdm:presentation:swagger")

findProject(":customer-mdm:container")?.name = "customer-mdm-container"
findProject(":customer-mdm:domain")?.name = "customer-mdm-domain"
findProject(":customer-mdm:domain:core")?.name = "customer-mdm-domain-core"
findProject(":customer-mdm:domain:application-service")?.name = "customer-mdm-application-service"
findProject(":customer-mdm:infrastructure")?.name = "customer-mdm-infrastructure"
findProject(":customer-mdm:infrastructure:data-access")?.name = "customer-mdm-data-access"
findProject(":customer-mdm:infrastructure:messaging")?.name = "customer-mdm-messaging"
findProject(":customer-mdm:presentation")?.name = "customer-mdm-presentation"
findProject(":customer-mdm:presentation:api-spec")?.name = "customer-mdm-api-spec"
findProject(":customer-mdm:presentation:rest-controller")?.name = "customer-mdm-rest-controller"
findProject(":customer-mdm:presentation:swagger")?.name = "customer-mdm-swagger"

include("e2e-testing")

include("order-service:container")
include("order-service:domain:core")
include("order-service:domain:application-service")
include("order-service:infrastructure:data-access")
include("order-service:infrastructure:messaging")
include("order-service:presentation:api-spec")
include("order-service:presentation:rest-controller")
include("order-service:presentation:swagger")

findProject(":order-service:container")?.name = "order-container"
findProject(":order-service:domain")?.name = "order-domain"
findProject(":order-service:domain:core")?.name = "order-domain-core"
findProject(":order-service:domain:application-service")?.name = "order-application-service"
findProject(":order-service:infrastructure")?.name = "order-infrastructure"
findProject(":order-service:infrastructure:data-access")?.name = "order-data-access"
findProject(":order-service:infrastructure:messaging")?.name = "order-messaging"
findProject(":order-service:presentation")?.name = "order-presentation"
findProject(":order-service:presentation:api-spec")?.name = "order-api-spec"
findProject(":order-service:presentation:rest-controller")?.name = "order-rest-controller"
findProject(":order-service:presentation:swagger")?.name = "order-swagger"

include("payment-service:container")
include("payment-service:domain:core")
include("payment-service:domain:application-service")
include("payment-service:infrastructure:data-access")
include("payment-service:infrastructure:messaging")

findProject(":payment-service:container")?.name = "payment-container"
findProject(":payment-service:domain")?.name = "payment-domain"
findProject(":payment-service:domain:core")?.name = "payment-domain-core"
findProject(":payment-service:domain:application-service")?.name = "payment-application-service"
findProject(":payment-service:infrastructure")?.name = "payment-infrastructure"
findProject(":payment-service:infrastructure:data-access")?.name = "payment-data-access"
findProject(":payment-service:infrastructure:messaging")?.name = "payment-messaging"

includeBuild("build-logic")
