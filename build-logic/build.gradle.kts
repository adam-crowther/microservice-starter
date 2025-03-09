plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(libs.groovy)

    implementation(libs.docker.compose.plugin)
    implementation(libs.avro.tools)
    implementation(libs.avro.plugin)

    implementation(libs.graphfity.plugin) {
        exclude(group = "org.codehaus.groovy", module = "groovy")
        exclude(group = "org.codehaus.groovy", module = "groovy-all")
    }
    implementation(libs.spotbugs.snom.plugin)
    implementation(libs.jackson.core)
    implementation(libs.jackson.dataformat.yaml)
    implementation(libs.liquibase.core)
    implementation(libs.liquibase.plugin)
    implementation(libs.openapi.generator.plugin)
    implementation(libs.spring.boot.plugin)
    implementation(libs.ratpack)
    implementation(libs.shadow)

    implementation(libs.spotbugs.plugin)

    testImplementation(libs.spock.bom)
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}
