plugins {
  id("java-conventions")
  id("spock-conventions")
}

dependencies {
  implementation(project(":common:common-domain"))
  implementation(project(":common:common-application"))
  implementation(project(":common:common-presentation"))
  implementation(project(":customer-mdm:customer-mdm-domain:customer-mdm-application-service"))
  implementation(project(":customer-mdm:customer-mdm-domain:customer-mdm-domain-core"))
  implementation(project(":customer-mdm:customer-mdm-presentation:customer-mdm-api-spec"))

  compileOnly(libs.jackson.annotations)
  compileOnly(libs.swagger.annotations)
  compileOnly(libs.jakarta.validation.api)

  implementation(libs.guava)
  implementation(libs.lombok)
  implementation(libs.mapstruct)
  implementation(libs.slf4j.api)
  implementation(libs.spring.beans)
  implementation(libs.spring.context)
  implementation(libs.spring.core)
  implementation(libs.spring.web)

  annotationProcessor(libs.mapstruct.processor)
  annotationProcessor(libs.lombok)
  annotationProcessor(libs.lombok.mapstruct.binding)
}
