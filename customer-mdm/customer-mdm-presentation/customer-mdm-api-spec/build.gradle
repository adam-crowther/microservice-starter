plugins {
  id 'java-conventions'
  id 'openapi-conventions'
}

dependencies {
  testFixturesImplementation testFixtures(project(":common:common-test"))

  testFixturesImplementation "com.fasterxml.jackson.core:jackson-annotations"
  testFixturesImplementation "org.apache.commons:commons-math3"
  testFixturesImplementation "org.projectlombok:lombok"
  testFixturesImplementation "org.springframework:spring-webflux"

  testFixturesAnnotationProcessor "org.projectlombok:lombok"
}

openApiGenerateSpring {
  apiPackage = "com.acroteq.ticketing.customer.service.presentation.api"
  modelPackage = "com.acroteq.ticketing.customer.service.presentation.model"
  invokerPackage = "com.acroteq.ticketing.customer.service.presentation.invoker"
}

openApiGenerateJavaClient {
  apiPackage = "com.acroteq.ticketing.customer.service.client.api"
  modelPackage = "com.acroteq.ticketing.customer.service.client.model"
  invokerPackage = "com.acroteq.ticketing.customer.service.client.invoker"
}