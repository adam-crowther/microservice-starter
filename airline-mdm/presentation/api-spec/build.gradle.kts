import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

plugins {
  id("java-conventions")
  id("openapi-conventions")
}

dependencies {
  testFixturesImplementation(testFixtures(project(":common:common-test")))

  testFixturesImplementation(libs.jackson.annotations)
  testFixturesImplementation(libs.lombok)
  testFixturesImplementation(libs.spring.webflux)
  testFixturesImplementation(libs.commons.math3)

  testFixturesAnnotationProcessor(libs.lombok)
}

tasks.named<GenerateTask>("openApiGenerateSpring") {
  apiPackage = "com.acroteq.ticketing.airline.service.presentation.api"
  modelPackage = "com.acroteq.ticketing.airline.service.presentation.model"
  invokerPackage = "com.acroteq.ticketing.airline.service.presentation.invoker"
}

tasks.named<GenerateTask>("openApiGenerateJavaClient") {
  apiPackage = "com.acroteq.ticketing.airline.service.client.api"
  modelPackage = "com.acroteq.ticketing.airline.service.client.model"
  invokerPackage = "com.acroteq.ticketing.airline.service.client.invoker"
}