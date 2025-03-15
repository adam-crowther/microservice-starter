plugins {
  id("java-conventions")
  id("spock-conventions")
}

dependencies {
  implementation(project(":common:common-helper"))
  implementation(project(":common:common-domain"))

  implementation(libs.commons.lang3)
  implementation(libs.commons.collections4)
  implementation(libs.guava)
  implementation(libs.jakarta.annotation.api)
  implementation(libs.lombok)
  implementation(libs.slf4j.api)

  annotationProcessor(libs.lombok)

  testRuntimeOnly(libs.slf4j.simple)
}
