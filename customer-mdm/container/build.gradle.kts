plugins {
  id("java-conventions")
  id("spring-conventions")
}

dependencies {
  implementation(project(":common:common-container"))
  implementation(project(":customer-mdm:customer-mdm-presentation:customer-mdm-rest-controller"))
  implementation(project(":customer-mdm:customer-mdm-domain:customer-mdm-domain-core"))
  implementation(project(":customer-mdm:customer-mdm-domain:customer-mdm-application-service"))
  implementation(project(":customer-mdm:customer-mdm-infrastructure:customer-mdm-data-access"))
  implementation(project(":customer-mdm:customer-mdm-infrastructure:customer-mdm-messaging"))

  implementation(libs.jul.to.slf4j)
  implementation(libs.spring.boot.autoconfigure)
  implementation(libs.spring.boot)
  implementation(libs.spring.context)
}
