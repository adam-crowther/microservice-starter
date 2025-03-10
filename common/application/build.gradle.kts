plugins {
  id 'java-conventions'
  id 'spock-conventions'
}

dependencies {
  implementation project(":common:common-domain")
  implementation project(":common:common-helper")

  implementation 'com.google.guava:guava:'
  implementation 'jakarta.annotation:jakarta.annotation-api'
  implementation 'jakarta.validation:jakarta.validation-api'
  implementation 'org.projectlombok:lombok'
  implementation 'org.mapstruct:mapstruct'
  implementation 'org.springframework:spring-context'

  annotationProcessor 'org.mapstruct:mapstruct-processor'
  annotationProcessor 'org.projectlombok:lombok'
  annotationProcessor 'org.projectlombok:lombok-mapstruct-binding'

  testRuntimeOnly 'org.springframework.boot:spring-boot-starter-validation'
}
