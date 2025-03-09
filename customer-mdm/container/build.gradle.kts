plugins {
  id 'java-conventions'
  id 'spring-conventions'
}

dependencies {
  implementation project(":common:common-container")
  implementation project(":customer-mdm:customer-mdm-presentation:customer-mdm-rest-controller")
  implementation project(":customer-mdm:customer-mdm-domain:customer-mdm-domain-core")
  implementation project(":customer-mdm:customer-mdm-domain:customer-mdm-application-service")
  implementation project(":customer-mdm:customer-mdm-infrastructure:customer-mdm-data-access")
  implementation project(":customer-mdm:customer-mdm-infrastructure:customer-mdm-messaging")

  implementation 'org.slf4j:jul-to-slf4j'
  implementation 'org.springframework.boot:spring-boot-autoconfigure'
  implementation 'org.springframework.boot:spring-boot'
  implementation 'org.springframework:spring-context'
}
