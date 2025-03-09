plugins {
  id 'java-conventions'
  id 'spock-conventions'
  id 'testcontainers-conventions'
}

dependencies {
  implementation project(':common:common-application')
  implementation project(':common:common-domain')
  implementation project(':common:common-helper')
  implementation project(':common:common-infrastructure')

  implementation 'org.apache.avro:avro'
  implementation 'jakarta.annotation:jakarta.annotation-api'
  implementation 'jakarta.validation:jakarta.validation-api'
  implementation 'org.apache.kafka:kafka-clients'
  implementation 'org.projectlombok:lombok'
  implementation 'org.slf4j:slf4j-api'
  implementation 'org.springframework.boot:spring-boot-autoconfigure'
  implementation 'org.springframework.boot:spring-boot'
  implementation 'org.springframework:spring-context'
  implementation 'org.springframework:spring-core'
  implementation 'org.springframework.kafka:spring-kafka'

  annotationProcessor 'org.projectlombok:lombok'
  annotationProcessor 'org.springframework.boot:spring-boot-configuration-processor'

  testImplementation 'org.springframework.boot:spring-boot-test'

  testAnnotationProcessor 'org.projectlombok:lombok'
}
