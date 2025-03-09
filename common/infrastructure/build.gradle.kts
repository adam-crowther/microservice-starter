plugins {
  id 'java-conventions'
  id 'spock-conventions'
  id 'testcontainers-conventions'
}

dependencies {
  implementation project(":common:common-application")
  implementation project(":common:common-domain")
  implementation project(":common:common-helper")

  implementation 'com.google.guava:guava'
  implementation 'jakarta.persistence:jakarta.persistence-api'
  implementation 'org.apache.avro:avro'
  implementation 'org.projectlombok:lombok'
  implementation 'org.mapstruct:mapstruct'
  implementation 'org.springframework:spring-context'
  implementation 'org.springframework.data:spring-data-commons'
  implementation 'org.springframework.data:spring-data-jpa'
  implementation 'org.springframework.security:spring-security-core'

  annotationProcessor 'org.mapstruct:mapstruct-processor'
  annotationProcessor 'org.projectlombok:lombok'
  annotationProcessor 'org.projectlombok:lombok-mapstruct-binding'

  testImplementation 'jakarta.annotation:jakarta.annotation-api'
  testImplementation 'org.springframework.boot:spring-boot-test'
  testImplementation 'org.springframework:spring-test'

  testAnnotationProcessor 'org.projectlombok:lombok'

  testFixturesImplementation 'com.github.spotbugs:spotbugs-annotations'
  testFixturesImplementation 'org.projectlombok:lombok'
  testFixturesImplementation 'org.slf4j:slf4j-api'

  testFixturesAnnotationProcessor 'org.projectlombok:lombok'

}
