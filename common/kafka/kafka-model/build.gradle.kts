plugins {
  id 'java-conventions'
  id 'avro-conventions'
}

dependencies {
  testFixturesImplementation testFixtures(project(":common:common-test"))

  testFixturesImplementation "org.apache.avro:avro"
  testFixturesImplementation "org.projectlombok:lombok"
  testFixturesImplementation "org.springframework:spring-context"

  testFixturesAnnotationProcessor "org.projectlombok:lombok"
}