plugins {
  id("java-conventions")
  id("testcontainers-conventions")
}

dependencies {
  testFixturesImplementation(libs.testcontainers.keycloak)
  testFixturesImplementation(libs.spotbugs.annotations)
  testFixturesImplementation(libs.kafka.avro.serializer)
  testFixturesImplementation(libs.reactor.kafka)
  testFixturesImplementation(libs.reactor.core)
  testFixturesImplementation(libs.avro)
  testFixturesImplementation(libs.commons.math3)
  testFixturesImplementation(libs.kafka.clients)
  testFixturesImplementation(libs.junit.jupiter)
  testFixturesImplementation(libs.lombok)
  testFixturesImplementation(libs.spring.boot.test)
  testFixturesImplementation(libs.spring.kafka)
  testFixturesImplementation(libs.spring.context)
  testFixturesImplementation(libs.spring.web)
  testFixturesImplementation(libs.spring.webflux)
  testFixturesImplementation(libs.testcontainers)
  testFixturesImplementation(libs.testcontainers.kafka)
  testFixturesImplementation(libs.testcontainers.postgresql)

  testFixturesAnnotationProcessor(libs.lombok)
}

sourceSets {
  test {
    resources {
      srcDirs("src/testFixtures/resources")
    }
  }
}