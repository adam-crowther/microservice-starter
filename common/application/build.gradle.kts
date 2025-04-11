plugins {
  id("java-conventions")
  id("spock-conventions")
}

dependencies {
  implementation(project(":common:common-domain"))
  implementation(project(":common:common-helper"))

  implementation(libs.avro)
  implementation(libs.guava)
  implementation(libs.jakarta.annotation.api)
  implementation(libs.jakarta.validation.api)
  implementation(libs.lombok)
  implementation(libs.mapstruct)
  implementation(libs.spring.context)

  annotationProcessor(libs.mapstruct.processor)
  annotationProcessor(libs.lombok)
  annotationProcessor(libs.lombok.mapstruct.binding)

  testImplementation(testFixtures(project(":common:common-domain")))
  testImplementation(libs.spock.spring)
  testImplementation(libs.spring.boot.test)
  testRuntimeOnly(libs.spring.boot.starter.validation)
}
