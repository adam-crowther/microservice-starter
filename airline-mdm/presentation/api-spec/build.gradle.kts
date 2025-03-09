plugins {
  id 'java-conventions'
  id 'openapi-conventions'
}

dependencies {
  testFixturesImplementation testFixtures(project(":common:common-test"))

  testFixturesImplementation "com.fasterxml.jackson.core:jackson-annotations"
  testFixturesImplementation "org.projectlombok:lombok"
  testFixturesImplementation "org.springframework:spring-webflux"
  testFixturesImplementation "org.apache.commons:commons-math3"

  testFixturesAnnotationProcessor "org.projectlombok:lombok"
}

openApiGenerateSpring {
  apiPackage = "com.acroteq.ticketing.airline.service.presentation.api"
  modelPackage = "com.acroteq.ticketing.airline.service.presentation.model"
  invokerPackage = "com.acroteq.ticketing.airline.service.presentation.invoker"
}

openApiGenerateJavaClient {
  apiPackage = "com.acroteq.ticketing.airline.service.client.api"
  modelPackage = "com.acroteq.ticketing.airline.service.client.model"
  invokerPackage = "com.acroteq.ticketing.airline.service.client.invoker"
}