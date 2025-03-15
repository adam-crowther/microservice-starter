plugins {
  id("java-conventions")
  id("spock-conventions")
}

dependencies {
  implementation(project(":common:common-application"))
  implementation(project(":common:common-domain"))
  implementation(project(":common:common-helper"))
  implementation(project(":common:common-infrastructure"))
  implementation(project(":airline-approval-service:airline-approval-domain:airline-approval-application-service"))
  implementation(project(":airline-approval-service:airline-approval-domain:airline-approval-domain-core"))
  implementation(project(":common:common-kafka:kafka-model"))
  implementation(project(":common:common-kafka:kafka-libs"))

  compileOnly(libs.spring.boot)
  compileOnly(libs.spring.kafka)

  implementation(libs.avro)
  implementation(libs.guava)
  implementation(libs.lombok)
  implementation(libs.mapstruct)
  implementation(libs.slf4j.api)
  implementation(libs.spring.beans)
  implementation(libs.spring.context)

  annotationProcessor(libs.mapstruct.processor)
  annotationProcessor(libs.lombok)
  annotationProcessor(libs.lombok.mapstruct.binding)
}
