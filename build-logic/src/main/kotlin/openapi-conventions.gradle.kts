import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

plugins {
    java
    id("org.openapi.generator")
}

val libs = versionCatalogs.named("libs")

dependencies {
    implementation(libs.findLibrary("jackson-annotations").get())
    implementation(libs.findLibrary("jakarta-annotation-api").get())
    implementation(libs.findLibrary("jakarta-servlet-api").get())
    implementation(libs.findLibrary("jakarta-validation-api").get())
    implementation(libs.findLibrary("lombok").get())
    implementation(libs.findLibrary("jackson-databind-nullable").get())
    implementation(libs.findLibrary("spotbugs-annotations").get())
    implementation(libs.findLibrary("spring-context").get())
    implementation(libs.findLibrary("spring-core").get())
    implementation(libs.findLibrary("spring-web").get())
    implementation(libs.findLibrary("spring-webflux").get())
    implementation(libs.findLibrary("swagger-annotations").get())
    implementation(libs.findLibrary("swagger-core").get())

    annotationProcessor(libs.findLibrary("swagger-annotations").get())
    annotationProcessor(libs.findLibrary("lombok").get())
}

tasks.register<GenerateTask>("openApiGenerateSpring") {
    group = "openapi tools"
    generatorName = "spring"
    inputSpec = layout.projectDirectory.file("src/main/spec/openapi.yaml").asFile.absolutePath
    outputDir = layout.buildDirectory.dir("generated/spring").get().asFile.absolutePath
    configOptions.putAll(
        mapOf(
            "dateLibrary" to "java8",
            "interfaceOnly" to "true",
            "useTags" to "true",
            "useSpringBoot3" to "true",
            "containerDefaultToNull" to "true",
            "additionalModelTypeAnnotations" to "@lombok.experimental.SuperBuilder"
        )
    )
}

tasks.register<GenerateTask>("openApiGenerateJavaClient") {
    group = "openapi tools"
    generatorName = "java"
    inputSpec = layout.projectDirectory.file("src/main/spec/openapi.yaml").asFile.absolutePath
    outputDir = layout.buildDirectory.dir("generated/java-client").get().asFile.absolutePath
    configOptions.putAll(
        mapOf(
            "dateLibrary" to "java8",
            "useJakartaEe" to "true",
            "library" to "webclient",
            "serializationLibrary" to "jackson",
            "containerDefaultToNull" to "true",
            "additionalModelTypeAnnotations" to "@lombok.experimental.SuperBuilder"
        )
    )
}

tasks.register<GenerateTask>("openApiGeneratePythonClient") {
    group = "openapi tools"
    generatorName = "python"
    inputSpec = layout.projectDirectory.file("src/main/spec/openapi.yaml").asFile.absolutePath
    outputDir = layout.buildDirectory.dir("generated/python-client").get().asFile.absolutePath
    configOptions.putAll(
        mapOf(
            "mapNumberTo" to "float",
            "projectName" to "risk_model",
            "packageName" to "openapi_client",
        )
    )
}

sourceSets {
    named("main") {
        java {
            srcDir(layout.buildDirectory.dir("generated/spring/src/main/java"))
            srcDir(layout.buildDirectory.dir("generated/java-client/src/main/java"))
        }
        resources {
            srcDir(file(layout.projectDirectory.file("src/main/spec")))
        }
    }
}

tasks.named("compileJava") {
    dependsOn(listOf("openApiGenerateSpring", "openApiGenerateJavaClient", "openApiGeneratePythonClient"))
}
