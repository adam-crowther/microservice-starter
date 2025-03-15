plugins {
  id("java-conventions")
  id("spring-conventions")
  id("testcontainers-conventions")
}

dependencies {
  implementation(project(":common:common-container"))
  implementation(project(":order-service:order-domain:order-domain-core"))
  implementation(project(":order-service:order-domain:order-application-service"))
  implementation(project(":order-service:order-presentation:order-rest-controller"))
  implementation(project(":order-service:order-infrastructure:order-data-access"))
  implementation(project(":order-service:order-infrastructure:order-messaging"))

  implementation(libs.jul.to.slf4j)
  implementation(libs.spring.boot.autoconfigure)
  implementation(libs.spring.boot)
  implementation(libs.spring.context)

  testImplementation(project(":common:common-kafka:kafka-model"))
  testImplementation(project(":order-service:order-presentation:order-api-spec"))
  testImplementation(testFixtures(project(":common:common-kafka:kafka-model")))
  testImplementation(testFixtures(project(":common:common-test")))

  testImplementation(libs.reactor.core)
  testImplementation(libs.avro)
  testImplementation(libs.lombok)
  testImplementation(libs.spring.boot.test)
  testImplementation(libs.spring.test)
  testImplementation(libs.spring.webflux)

  testAnnotationProcessor(libs.lombok)

  testRuntimeOnly(libs.postgresql)
  testRuntimeOnly(libs.log4j.core)
  testRuntimeOnly(libs.log4j.layout.template.json)
  testRuntimeOnly(libs.log4j.slf4j2.impl)
  
  testFixturesImplementation(project(":common:common-helper"))
  testFixturesImplementation(project(":order-service:order-infrastructure:order-data-access"))
  testFixturesImplementation(project(":common:common-kafka:kafka-model"))
  testFixturesImplementation(project(":order-service:order-presentation:order-api-spec"))
  testFixturesImplementation(testFixtures(project(":common:common-test")))
  testFixturesImplementation(testFixtures(project(":order-service:order-presentation:order-api-spec")))

  testFixturesImplementation(libs.avro)
  testFixturesImplementation(libs.awaitility)
  testFixturesImplementation(libs.lombok)
  testFixturesImplementation(libs.spring.boot.test)
  testFixturesImplementation(libs.spring.data.jpa)
  testFixturesImplementation(libs.spring.security.config)
  testFixturesImplementation(libs.spring.security.web)
  testFixturesImplementation(libs.spring.context)

  testFixturesAnnotationProcessor(libs.lombok)
}
