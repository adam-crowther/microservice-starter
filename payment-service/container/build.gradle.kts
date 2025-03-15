plugins {
  id("java-conventions")
  id("spring-conventions")
}

dependencies {
  implementation(project(":common:common-container"))
  implementation(project(":common:common-domain"))
  implementation(project(":payment-service:payment-domain:payment-domain-core"))
  implementation(project(":payment-service:payment-domain:payment-application-service"))
  implementation(project(":payment-service:payment-infrastructure:payment-data-access"))
  implementation(project(":payment-service:payment-infrastructure:payment-messaging"))

  implementation(libs.jul.to.slf4j)
  implementation(libs.spring.boot.autoconfigure)
  implementation(libs.spring.boot)
  implementation(libs.spring.context)
}
