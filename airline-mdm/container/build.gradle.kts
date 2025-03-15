plugins {
  id("java-conventions")
  id("spring-conventions")
}

dependencies {
  implementation(project(":common:common-container"))
  implementation(project(":common:common-domain"))
  implementation(project(":airline-mdm:airline-mdm-presentation:airline-mdm-rest-controller"))
  implementation(project(":airline-mdm:airline-mdm-domain:airline-mdm-domain-core"))
  implementation(project(":airline-mdm:airline-mdm-domain:airline-mdm-application-service"))
  implementation(project(":airline-mdm:airline-mdm-infrastructure:airline-mdm-data-access"))
  implementation(project(":airline-mdm:airline-mdm-infrastructure:airline-mdm-messaging"))

  implementation(libs.jul.to.slf4j)
  implementation(libs.spring.boot.autoconfigure)
  implementation(libs.spring.boot)
  implementation(libs.spring.context)
}
