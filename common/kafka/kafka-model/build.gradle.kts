plugins {
  id("java-conventions")
  id("avro-conventions")
}

dependencies {
  testFixturesImplementation(testFixtures(project(":common:common-test")))

  testFixturesImplementation(libs.avro)
  testFixturesImplementation(libs.lombok)
  testFixturesImplementation(libs.spring.context)

  testFixturesAnnotationProcessor(libs.lombok)
}