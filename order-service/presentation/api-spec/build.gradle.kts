plugins {
  id 'java-conventions'
  id 'openapi-conventions'
}

dependencies {
  testFixturesImplementation testFixtures(project(":common:common-test"))

  testFixturesImplementation "com.fasterxml.jackson.core:jackson-annotations"
  testFixturesImplementation "org.hamcrest:hamcrest"
  testFixturesImplementation "org.projectlombok:lombok"
  testFixturesImplementation "org.springframework:spring-webflux"

  testFixturesAnnotationProcessor "org.projectlombok:lombok"
}

openApiGenerateSpring {
  apiPackage = "com.acroteq.ticketing.order.service.presentation.api"
  modelPackage = "com.acroteq.ticketing.order.service.presentation.model"
  invokerPackage = "com.acroteq.ticketing.order.service.presentation.invoker"
}

openApiGenerateJavaClient {
  apiPackage = "com.acroteq.ticketing.order.service.client.api"
  modelPackage = "com.acroteq.ticketing.order.service.client.model"
  invokerPackage = "com.acroteq.ticketing.order.service.client.invoker"
}
