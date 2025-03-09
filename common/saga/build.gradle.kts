plugins {
  id 'java-conventions'
  id 'spock-conventions'
}

dependencies {
  implementation project(':common:common-application')
  implementation project(':common:common-domain')

  annotationProcessor 'org.projectlombok:lombok'
}
