plugins {
  id 'java-conventions'
  id 'spock-conventions'
}

dependencies {
  implementation project(":common:common-domain")
  implementation project(":common:common-application")
  implementation project(":common:common-presentation")
  implementation project(":order-service:order-domain:order-application-service")
  implementation project(":order-service:order-domain:order-domain-core")
  implementation project(":order-service:order-presentation:order-api-spec")

  compileOnly 'com.fasterxml.jackson.core:jackson-annotations'
  compileOnly 'io.swagger.core.v3:swagger-annotations'
  compileOnly 'jakarta.validation:jakarta.validation-api'

  implementation 'com.google.guava:guava'
  implementation 'org.projectlombok:lombok'
  implementation 'org.mapstruct:mapstruct'
  implementation 'org.slf4j:slf4j-api'
  implementation 'org.springframework:spring-beans'
  implementation 'org.springframework:spring-context'
  implementation 'org.springframework:spring-core'
  implementation 'org.springframework:spring-web'

  annotationProcessor 'org.mapstruct:mapstruct-processor'
  annotationProcessor 'org.projectlombok:lombok'
  annotationProcessor 'org.projectlombok:lombok-mapstruct-binding'
}
