plugins {
    id("java-conventions")
    id("groovy-conventions")
    // idea
    id("io.ratpack.ratpack-groovy")
    id("com.github.johnrengelman.shadow")
}

val libs = versionCatalogs.named("libs")

val swaggerUiBundle: Configuration = configurations.create("swaggerUiBundle")
val commonSwaggerBundle: Configuration = configurations.create("commonSwaggerBundle")

dependencies {
    runtimeOnly(libs.findLibrary("slf4j-simple").get())

    commonSwaggerBundle(project(":common:common-swagger"))
    swaggerUiBundle(libs.findLibrary("swagger-ui").get())
}

val swaggerUiDirectory: Provider<Directory> = layout.buildDirectory.dir("swagger-ui")
tasks.register<Copy>("extractSwaggerUI") {
    group = "swagger"
    from(
        zipTree(
            configurations.named("swaggerUiBundle").get().singleFile
        ).matching {
            include("META-INF/resources/webjars/swagger-ui/**")
            exclude("**/swagger-initializer.js")
        }
    )
    eachFile { path = name }
    includeEmptyDirs = false
    into(swaggerUiDirectory)
}

tasks.register<Copy>("copySwaggerUIInitialiser") {
    dependsOn(":common:common-swagger:jar")
    group = "swagger"
    from(configurations.named("commonSwaggerBundle").get().map { if (it.isDirectory) it else zipTree(it) })
    {
        include("swagger-initializer.js")
    }
    into(swaggerUiDirectory)
}

val ratpackDirectory: Provider<Directory> = layout.buildDirectory.dir("ratpack")
val swaggerServerPort: String by project
tasks.register<Copy>("copyRatpackGroovy") {
    dependsOn(":common:common-swagger:jar")
    group = "swagger"
    from(configurations.named("commonSwaggerBundle").get().map { if (it.isDirectory) it else zipTree(it) }) {
        include("ratpack.groovy")
    }
    filter { line -> line.replace("%PORT%", swaggerServerPort) }
    into(ratpackDirectory)
}

val openApiSpecSubmodule: String by project
val specProject = project(openApiSpecSubmodule)
val restServerHost: String by project
val restServerPort: String by project
val restServerDescription: String by project
tasks.register<Copy>("copyOpenApiSpec") {
    group = "swagger"
    from(specProject.layout.files("src/main/spec/"))
    include("**/*.yaml")
    into(swaggerUiDirectory)

    doLast {
        val specFile = swaggerUiDirectory.get().file("openapi.yaml").asFile
        specFile.appendText(
            "\nservers:" +
                    "\n  - url: //${restServerHost}:${restServerPort}" +
                    "\n    description: ${restServerDescription}\n"
        )
    }
}

tasks.named("processResources") {
    dependsOn(tasks.named("extractSwaggerUI"))
    dependsOn(tasks.named("copySwaggerUIInitialiser"))
    dependsOn(tasks.named("copyRatpackGroovy"))
    dependsOn(tasks.named("copyOpenApiSpec"))
}

sourceSets.named("main") {
    resources.srcDirs(
        swaggerUiDirectory,
    )
}

ratpack.baseDir = layout.buildDirectory.dir("ratpack").get().asFile

tasks.named<JavaExec>("run") {
    dependsOn(tasks.named("processResources"))

    group = "swagger"
    systemProperties["ratpack.reloadable"] = "true"
}

tasks.named<Jar>("jar") {
    dependsOn(tasks.named("processResources"))

    manifest {
        attributes["Main-Class"] = "GroovyRatpackMain"
    }
}
