plugins {
  id("java-conventions")
  id("spock-conventions")
  id("testcontainers-conventions")
}

dependencies {
  implementation(project(":common:common-application"))
  implementation(project(":common:common-domain"))
  implementation(project(":common:common-helper"))
  implementation(project(":common:common-infrastructure"))

  implementation(libs.avro)
  implementation(libs.jakarta.annotation.api)
  implementation(libs.jakarta.validation.api)
  implementation(libs.kafka.clients)
  implementation(libs.lombok)
  implementation(libs.slf4j.api)
  implementation(libs.spring.boot.autoconfigure)
  implementation(libs.spring.boot)
  implementation(libs.spring.context)
  implementation(libs.spring.core)
  implementation(libs.spring.kafka)

  annotationProcessor(libs.lombok)
  annotationProcessor(libs.spring.boot.configuration.processor)

  testImplementation(libs.spring.boot.test)

  testAnnotationProcessor(libs.lombok)
}
