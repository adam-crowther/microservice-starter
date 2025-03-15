plugins {
  id("java-conventions")
  id("spock-conventions")
}

dependencies {
  implementation(project(":common:common-application"))
  implementation(project(":common:common-domain"))
  implementation(project(":common:common-helper"))

  implementation(libs.guava)
  implementation(libs.jakarta.annotation.api)
  implementation(libs.jakarta.validation.api)
  implementation(libs.lombok)
  implementation(libs.slf4j.api)
  implementation(libs.spring.context)
  implementation(libs.spring.core)
  implementation(libs.spring.web)
  implementation(libs.spring.webmvc)

  annotationProcessor(libs.mapstruct.processor)
  annotationProcessor(libs.lombok)
  annotationProcessor(libs.lombok.mapstruct.binding)
}
