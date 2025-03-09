plugins {
  id 'java-conventions'
  id 'spock-conventions'
}

dependencies {
  implementation 'org.projectlombok:lombok'
  implementation 'org.springframework:spring-context'
  implementation 'org.springframework:spring-core'
  implementation 'org.springframework.boot:spring-boot'
  implementation 'org.springframework.boot:spring-boot-autoconfigure'
  implementation 'org.springframework.security:spring-security-oauth2-resource-server'
  implementation 'org.springframework.security:spring-security-config'
  implementation 'org.springframework.security:spring-security-core'
  implementation 'org.springframework.security:spring-security-oauth2-jose'
  implementation 'org.springframework.security:spring-security-web'

  annotationProcessor 'org.projectlombok:lombok'
}
