plugins {
  id("java-conventions")
  id("spock-conventions")
  id("liquibase-conventions")
}

dependencies {
  implementation(project(":common:common-domain"))
  implementation(project(":common:common-application"))
  implementation(project(":common:common-helper"))
  implementation(project(":common:common-infrastructure"))
  implementation(project(":airline-mdm:airline-mdm-domain:airline-mdm-domain-core"))
  implementation(project(":airline-mdm:airline-mdm-domain:airline-mdm-application-service"))

  implementation(libs.guava)
  implementation(libs.jakarta.persistence.api)
  implementation(libs.lombok)
  implementation(libs.mapstruct)
  implementation(libs.spring.boot.autoconfigure)
  implementation(libs.spring.context)
  implementation(libs.spring.data.commons)
  implementation(libs.spring.data.jpa)

  annotationProcessor(libs.mapstruct.processor)
  annotationProcessor(libs.lombok)
  annotationProcessor(libs.lombok.mapstruct.binding)
}

