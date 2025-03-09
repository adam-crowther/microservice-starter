plugins {
  id 'java-conventions'
  id 'spock-conventions'
}

dependencies {
  implementation project(":common:common-application")
  implementation project(":common:common-domain")
  implementation project(":common:common-helper")
  implementation project(":common:common-infrastructure")
  implementation project(":airline-approval-service:airline-approval-domain:airline-approval-application-service")
  implementation project(":airline-approval-service:airline-approval-domain:airline-approval-domain-core")
  implementation project(":common:common-kafka:kafka-model")
  implementation project(":common:common-kafka:kafka-libs")

  compileOnly 'org.springframework.boot:spring-boot'
  compileOnly 'org.springframework.kafka:spring-kafka'

  implementation 'org.apache.avro:avro'
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
