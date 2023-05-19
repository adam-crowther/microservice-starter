plugins {
  id 'base'
  id 'maven-publish'
  id 'gradlelint-conventions'
}

allprojects {
  repositories {
    mavenCentral()
    maven {
      url "https://packages.confluent.io/maven/"
    }
  }
}

configurations.configureEach {
  resolutionStrategy.failOnVersionConflict()
}