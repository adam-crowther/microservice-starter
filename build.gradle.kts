plugins {
    base
    `maven-publish`
}

allprojects {
    repositories {
        mavenCentral()
        maven {
            url = uri("https://packages.confluent.io/maven/")
        }
    }
}

configurations.configureEach {
    resolutionStrategy.failOnVersionConflict()
}
