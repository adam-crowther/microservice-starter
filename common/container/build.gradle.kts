plugins {
  id("java-conventions")
  id("spock-conventions")
}

dependencies {
  implementation(libs.lombok)
  implementation(libs.spring.context)
  implementation(libs.spring.core)
  implementation(libs.spring.boot)
  implementation(libs.spring.boot.autoconfigure)
  implementation(libs.spring.boot.starter.data.jpa)
  implementation(libs.spring.security.oauth2.resource.server)
  implementation(libs.spring.security.config)
  implementation(libs.spring.security.core)
  implementation(libs.spring.security.oauth2.jose)
  implementation(libs.spring.security.web)

  annotationProcessor(libs.lombok)
}
