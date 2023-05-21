plugins {
  id 'java-conventions'
  id 'spring-conventions'
}

dependencies {
  implementation project(":common:common-container")
  implementation project(":common:common-domain")
  implementation project(":airline-approval-service:airline-approval-domain:airline-approval-domain-core")
  implementation project(":airline-approval-service:airline-approval-domain:airline-approval-application-service")
  implementation project(":airline-approval-service:airline-approval-infrastructure:airline-approval-data-access")
  implementation project(":airline-approval-service:airline-approval-infrastructure:airline-approval-messaging")

  implementation 'org.slf4j:jul-to-slf4j'
  implementation 'org.springframework.boot:spring-boot-autoconfigure'
  implementation 'org.springframework.boot:spring-boot'
  implementation 'org.springframework:spring-context'
}