plugins {
  id 'java-conventions'
  id 'spock-conventions'
}

dependencies {
  implementation project(":common:common-application")
  implementation project(":common:common-domain")
  implementation project(":common:common-helper")
  implementation project(":common:common-infrastructure")
  implementation project(":common:common-kafka:kafka-libs")
  implementation project(":common:common-kafka:kafka-model")
  implementation project(":order-service:order-domain:order-application-service")
  implementation project(":order-service:order-domain:order-domain-core")

  compileOnly 'org.springframework.kafka:spring-kafka'
  compileOnly 'org.springframework.boot:spring-boot'

  implementation 'org.apache.avro:avro'
  implementation 'com.google.guava:guava'
  implementation 'org.projectlombok:lombok'
  implementation 'org.mapstruct:mapstruct'
  implementation 'org.slf4j:slf4j-api'
  implementation 'org.springframework:spring-beans'
  implementation 'org.springframework:spring-context'
  implementation 'org.springframework.kafka:spring-kafka'
  implementation 'org.springframework:spring-messaging'

  annotationProcessor 'org.mapstruct:mapstruct-processor'
  annotationProcessor 'org.projectlombok:lombok'
  annotationProcessor 'org.projectlombok:lombok-mapstruct-binding'
}
