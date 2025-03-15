plugins {
  id("java-conventions")
  id("spock-conventions")
  id("testcontainers-conventions")
}

dependencies {
  implementation(project(":common:common-application"))
  implementation(project(":common:common-domain"))
  implementation(project(":common:common-helper"))


  implementation(libs.guava)
  implementation(libs.jakarta.persistence.api)
  implementation(libs.avro)
  implementation(libs.lombok)
  implementation(libs.mapstruct)
  implementation(libs.spring.context)
  implementation(libs.spring.data.commons)
  implementation(libs.spring.data.jpa)
  implementation(libs.spring.security.core)

  annotationProcessor(libs.mapstruct.processor)
  annotationProcessor(libs.lombok)
  annotationProcessor(libs.lombok.mapstruct.binding)

  testImplementation(libs.jakarta.annotation.api)
  testImplementation(libs.spring.boot.test)
  testImplementation(libs.spring.test)

  testAnnotationProcessor(libs.lombok)

  testFixturesImplementation(libs.spotbugs.annotations)
  testFixturesImplementation(libs.lombok)
  testFixturesImplementation(libs.slf4j.api)

  testFixturesAnnotationProcessor(libs.lombok)
}
