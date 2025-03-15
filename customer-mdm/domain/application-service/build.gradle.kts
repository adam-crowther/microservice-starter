plugins {
  id("java-conventions")
  id("spock-conventions")
}

dependencies {
  implementation(project(":common:common-domain"))
  implementation(project(":common:common-application"))
  implementation(project(":common:common-helper"))
  implementation(project(":customer-mdm:customer-mdm-domain:customer-mdm-domain-core"))
  implementation(project("::common:common-saga"))

  implementation(libs.guava)
  implementation(libs.jakarta.annotation.api)
  implementation(libs.jakarta.validation.api)
  implementation(libs.lombok)
  implementation(libs.mapstruct)
  implementation(libs.slf4j.api)
  implementation(libs.spring.beans)
  implementation(libs.spring.boot)
  implementation(libs.spring.context)
  implementation(libs.spring.tx)

  annotationProcessor(libs.mapstruct.processor)
  annotationProcessor(libs.lombok)
  annotationProcessor(libs.lombok.mapstruct.binding)
  annotationProcessor(libs.spring.boot.configuration.processor)
}
