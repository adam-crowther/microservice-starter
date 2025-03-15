plugins {
    java
    `java-test-fixtures`
}

val libs = versionCatalogs.named("libs")

dependencies {
    implementation(libs.findLibrary("testcontainers-bom").get())
    implementation(libs.findLibrary("testcontainers-keycloak").get())

    testImplementation(libs.findLibrary("testcontainers").get())
    testImplementation(libs.findLibrary("testcontainers-keycloak").get())
    testImplementation(libs.findLibrary("testcontainers-kafka").get())
    testImplementation(libs.findLibrary("testcontainers-postgresql").get())
    testImplementation(libs.findLibrary("testcontainers-junit-jupiter").get())
    testImplementation(libs.findLibrary("keycloak-admin-client").get())

    testFixturesImplementation(libs.findLibrary("testcontainers-bom").get())
    testFixturesImplementation(libs.findLibrary("testcontainers-keycloak").get())
}