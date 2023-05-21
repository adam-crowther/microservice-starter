plugins {
  id 'java-conventions'
  id 'spock-conventions'
  id 'liquibase-conventions'
}

dependencies {
  implementation project(":common:common-domain")
  implementation project(":common:common-application")
  implementation project(":common:common-helper")
  implementation project(":common:common-infrastructure")
  implementation project(":payment-service:payment-domain:payment-domain-core")
  implementation project(":payment-service:payment-domain:payment-application-service")

  implementation 'jakarta.persistence:jakarta.persistence-api'
  implementation 'org.projectlombok:lombok'
  implementation 'org.mapstruct:mapstruct'
  implementation 'org.springframework.boot:spring-boot-autoconfigure'
  implementation 'org.springframework:spring-context'
  implementation 'org.springframework.data:spring-data-commons'
  implementation 'org.springframework.data:spring-data-jpa'

  annotationProcessor 'org.mapstruct:mapstruct-processor'
  annotationProcessor 'org.projectlombok:lombok'
  annotationProcessor 'org.projectlombok:lombok-mapstruct-binding'
}

