plugins {
  id 'java-conventions'
  id 'spock-conventions'
  id 'cucumber-conventions'
}

dependencies {
  implementation project(":common:common-domain")
  implementation project(":common:common-helper")

  implementation 'jakarta.annotation:jakarta.annotation-api'
  implementation 'org.projectlombok:lombok'
  implementation 'org.slf4j:slf4j-api'

  annotationProcessor 'org.projectlombok:lombok'

  testAnnotationProcessor 'org.projectlombok:lombok'

  testRuntimeOnly 'org.slf4j:slf4j-simple'
}
