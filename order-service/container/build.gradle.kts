plugins {
  id 'java-conventions'
  id 'spring-conventions'
  id 'testcontainers-conventions'
}

dependencies {
  implementation project(":common:common-container")
  implementation project(":order-service:order-domain:order-domain-core")
  implementation project(":order-service:order-domain:order-application-service")
  implementation project(":order-service:order-presentation:order-rest-controller")
  implementation project(":order-service:order-infrastructure:order-data-access")
  implementation project(":order-service:order-infrastructure:order-messaging")

  implementation 'org.slf4j:jul-to-slf4j'
  implementation 'org.springframework.boot:spring-boot-autoconfigure'
  implementation 'org.springframework.boot:spring-boot'
  implementation 'org.springframework:spring-context'

  testImplementation project(":common:common-kafka:kafka-model")
  testImplementation project(":order-service:order-presentation:order-api-spec")
  testImplementation testFixtures(project(":common:common-kafka:kafka-model"))
  testImplementation testFixtures(project(":common:common-test"))

  testImplementation 'io.projectreactor:reactor-core'
  testImplementation 'org.apache.avro:avro'
  testImplementation 'org.projectlombok:lombok'
  testImplementation 'org.springframework.boot:spring-boot-test'
  testImplementation 'org.springframework:spring-test'
  testImplementation 'org.springframework:spring-webflux'

  testAnnotationProcessor 'org.projectlombok:lombok'

  testRuntimeOnly 'org.postgresql:postgresql'
  testRuntimeOnly 'org.apache.logging.log4j:log4j-core'
  testRuntimeOnly 'org.apache.logging.log4j:log4j-layout-template-json'
  testRuntimeOnly 'org.apache.logging.log4j:log4j-slf4j2-impl'
  
  testFixturesImplementation project(":common:common-helper")
  testFixturesImplementation project(":order-service:order-infrastructure:order-data-access")
  testFixturesImplementation project(":common:common-kafka:kafka-model")
  testFixturesImplementation project(":order-service:order-presentation:order-api-spec")
  testFixturesImplementation testFixtures(project(":common:common-test"))
  testFixturesImplementation testFixtures(project(":order-service:order-presentation:order-api-spec"))

  testFixturesImplementation 'org.apache.avro:avro'
  testFixturesImplementation 'org.awaitility:awaitility'
  testFixturesImplementation 'org.projectlombok:lombok'
  testFixturesImplementation 'org.springframework.boot:spring-boot-test'
  testFixturesImplementation 'org.springframework.data:spring-data-jpa'
  testFixturesImplementation 'org.springframework.security:spring-security-config'
  testFixturesImplementation 'org.springframework.security:spring-security-web'
  testFixturesImplementation 'org.springframework:spring-context'

  testFixturesAnnotationProcessor 'org.projectlombok:lombok'
}
