plugins {
  id 'java-conventions'
  id 'testcontainers-conventions'
}

dependencies {
  testFixturesImplementation 'com.github.dasniko:testcontainers-keycloak'
  testFixturesImplementation 'com.github.spotbugs:spotbugs-annotations'
  testFixturesImplementation 'io.confluent:kafka-avro-serializer'
  testFixturesImplementation 'io.projectreactor.kafka:reactor-kafka'
  testFixturesImplementation 'io.projectreactor:reactor-core'
  testFixturesImplementation 'org.apache.avro:avro'
  testFixturesImplementation 'org.apache.commons:commons-math3'
  testFixturesImplementation 'org.apache.kafka:kafka-clients'
  testFixturesImplementation 'org.junit.jupiter:junit-jupiter'
  testFixturesImplementation 'org.projectlombok:lombok'
  testFixturesImplementation 'org.springframework.boot:spring-boot-test'
  testFixturesImplementation 'org.springframework.kafka:spring-kafka'
  testFixturesImplementation 'org.springframework:spring-context'
  testFixturesImplementation 'org.springframework:spring-web'
  testFixturesImplementation "org.springframework:spring-webflux"
  testFixturesImplementation 'org.testcontainers:testcontainers'
  testFixturesImplementation 'org.testcontainers:kafka'
  testFixturesImplementation 'org.testcontainers:postgresql'

  testFixturesAnnotationProcessor 'org.projectlombok:lombok'
}

sourceSets {
  test {
    resources {
      srcDirs "src/testFixtures/resources"
    }
  }
}