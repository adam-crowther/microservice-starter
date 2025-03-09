plugins {
  id 'java-conventions'
  id 'spring-conventions'
}

dependencies {
  implementation project(":common:common-container")
  implementation project(":common:common-domain")
  implementation project(":payment-service:payment-domain:payment-domain-core")
  implementation project(":payment-service:payment-domain:payment-application-service")
  implementation project(":payment-service:payment-infrastructure:payment-data-access")
  implementation project(":payment-service:payment-infrastructure:payment-messaging")

  implementation 'org.slf4j:jul-to-slf4j'
  implementation 'org.springframework.boot:spring-boot-autoconfigure'
  implementation 'org.springframework.boot:spring-boot'
  implementation 'org.springframework:spring-context'
}
