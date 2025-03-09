plugins {
  id 'java-conventions'
  id 'spock-conventions'
}

dependencies {
  implementation project(":common:common-domain")
  implementation project(":common:common-application")
  implementation project(":common:common-helper")
  implementation project(":customer-mdm:customer-mdm-domain:customer-mdm-domain-core")
  implementation project("::common:common-saga")

  implementation 'jakarta.validation:jakarta.validation-api'
  implementation 'org.projectlombok:lombok'
  implementation 'org.mapstruct:mapstruct'
  implementation 'org.slf4j:slf4j-api'
  implementation 'org.springframework:spring-beans'
  implementation 'org.springframework.boot:spring-boot'
  implementation 'org.springframework:spring-context'
  implementation 'org.springframework:spring-tx'

  annotationProcessor 'org.mapstruct:mapstruct-processor'
  annotationProcessor 'org.projectlombok:lombok'
  annotationProcessor 'org.projectlombok:lombok-mapstruct-binding'
  annotationProcessor 'org.springframework.boot:spring-boot-configuration-processor'
}
