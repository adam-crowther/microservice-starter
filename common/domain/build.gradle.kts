plugins {
  id("java-conventions")
  id("spock-conventions")
}

dependencies {
  implementation(project(":common:common-helper"))

  implementation(libs.commons.lang3)
  implementation(libs.guava)
  implementation(libs.jakarta.annotation.api)
  implementation(libs.lombok)
  annotationProcessor(libs.lombok)

  testFixturesImplementation(libs.lombok)
  testFixturesAnnotationProcessor(libs.lombok)
}
