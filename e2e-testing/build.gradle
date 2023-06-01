plugins {
  id 'java-conventions'
  id 'testcontainers-conventions'
}

dependencies {
  testImplementation project(":airline-mdm:airline-mdm-presentation:airline-mdm-api-spec")
  testImplementation project(":customer-mdm:customer-mdm-presentation:customer-mdm-api-spec")
  testImplementation project(":order-service:order-presentation:order-api-spec")
  testImplementation testFixtures(project(":common:common-infrastructure"))
  testImplementation testFixtures(project(":common:common-test"))
  testImplementation testFixtures(project(":airline-mdm:airline-mdm-presentation:airline-mdm-api-spec"))
  testImplementation testFixtures(project(":customer-mdm:customer-mdm-presentation:customer-mdm-api-spec"))
  testImplementation testFixtures(project(":order-service:order-presentation:order-api-spec"))

  testImplementation 'org.projectlombok:lombok'
  testImplementation 'org.springframework:spring-webflux'

  testAnnotationProcessor 'org.projectlombok:lombok'

  testFixturesImplementation testFixtures(project(":common:common-infrastructure"))
  testFixturesImplementation testFixtures(project(":common:common-test"))
  testFixturesImplementation testFixtures(project(":airline-mdm:airline-mdm-presentation:airline-mdm-api-spec"))
  testFixturesImplementation testFixtures(project(":customer-mdm:customer-mdm-presentation:customer-mdm-api-spec"))
  testFixturesImplementation testFixtures(project(":order-service:order-presentation:order-api-spec"))

  testFixturesImplementation 'com.github.dasniko:testcontainers-keycloak'
  testFixturesImplementation 'com.github.spotbugs:spotbugs-annotations'
  testFixturesImplementation 'org.springframework:spring-webflux'
  testFixturesImplementation 'org.testcontainers:testcontainers'
  testFixturesImplementation 'org.testcontainers:kafka'
  testFixturesImplementation 'org.testcontainers:postgresql'
  testFixturesImplementation 'org.projectlombok:lombok'

  testFixturesAnnotationProcessor 'org.projectlombok:lombok'

  testRuntimeOnly 'org.postgresql:postgresql'
  testRuntimeOnly 'org.apache.logging.log4j:log4j-core'
  testRuntimeOnly 'org.apache.logging.log4j:log4j-layout-template-json'
  testRuntimeOnly 'org.apache.logging.log4j:log4j-slf4j2-impl'
}