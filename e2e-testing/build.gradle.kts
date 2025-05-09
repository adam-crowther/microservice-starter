plugins {
    id("java-conventions")
    id("testcontainers-conventions")
}

dependencies {
    testImplementation(project(":airline-mdm:airline-mdm-presentation:airline-mdm-api-spec"))
    testImplementation(project(":customer-mdm:customer-mdm-presentation:customer-mdm-api-spec"))
    testImplementation(project(":order-service:order-presentation:order-api-spec"))

    testImplementation(testFixtures(project(":common:common-infrastructure")))
    testImplementation(testFixtures(project(":common:common-test")))
    testImplementation(testFixtures(project(":airline-mdm:airline-mdm-presentation:airline-mdm-api-spec")))
    testImplementation(testFixtures(project(":customer-mdm:customer-mdm-presentation:customer-mdm-api-spec")))
    testImplementation(testFixtures(project(":order-service:order-presentation:order-api-spec")))

    testImplementation(libs.lombok)
    testImplementation(libs.spring.webflux)

    testAnnotationProcessor(libs.lombok)

    testFixturesImplementation(testFixtures(project(":common:common-infrastructure")))
    testFixturesImplementation(testFixtures(project(":common:common-test")))

    testFixturesImplementation(testFixtures(project(":airline-mdm:airline-mdm-presentation:airline-mdm-api-spec")))
    testFixturesImplementation(testFixtures(project(":customer-mdm:customer-mdm-presentation:customer-mdm-api-spec")))
    testFixturesImplementation(testFixtures(project(":order-service:order-presentation:order-api-spec")))

    testFixturesImplementation(libs.spotbugs.annotations)
    testFixturesImplementation(libs.spring.webflux)
    testFixturesImplementation(libs.testcontainers)
    testFixturesImplementation(libs.testcontainers.kafka)
    testFixturesImplementation(libs.testcontainers.keycloak)
    testFixturesImplementation(libs.testcontainers.postgresql)
    testFixturesImplementation(libs.lombok)

    testFixturesAnnotationProcessor(libs.lombok)

    testRuntimeOnly(libs.postgresql)
    testRuntimeOnly(libs.log4j.core)
    testRuntimeOnly(libs.log4j.layout.template.json)
    testRuntimeOnly(libs.log4j.slf4j2.impl)
}

tasks.named("test") {
    dependsOn(
        project(":airline-approval-service:airline-approval-container").tasks.named("bootBuildImage"),
        project(":airline-mdm:airline-mdm-container").tasks.named("bootBuildImage"),
        project(":customer-mdm:customer-mdm-container").tasks.named("bootBuildImage"),
        project(":order-service:order-container").tasks.named("bootBuildImage"),
        project(":payment-service:payment-container").tasks.named("bootBuildImage"),
    )
}