plugins {
  id("java-conventions")
  id("spock-conventions")
}

dependencies {
  implementation(libs.lombok)

  annotationProcessor(libs.lombok)
}
