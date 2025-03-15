import com.github.ivancarras.graphfity.plugin.main.GraphfityPluginExtension
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
    java
    id("org.springframework.boot")
    id("com.github.ivancarras.graphfity")
}

configurations.runtimeOnly {
    exclude(group = "ch.qos.logback")
    exclude(group = "org.apache.logging.log4j", module = "log4j-to-slf4j")
}

val libs = versionCatalogs.named("libs")

dependencies {
    runtimeOnly(libs.findLibrary("jackson-databind").get())
    runtimeOnly(libs.findLibrary("jackson-dataformat-yaml").get())
    runtimeOnly(libs.findLibrary("log4j-core").get())
    runtimeOnly(libs.findLibrary("log4j-layout-template-json").get())
    runtimeOnly(libs.findLibrary("log4j-slf4j2-impl").get())
    runtimeOnly(libs.findLibrary("postgresql").get())
    runtimeOnly(libs.findLibrary("spring-boot-starter-data-jpa").get())
    runtimeOnly(libs.findLibrary("spring-boot-starter-oauth2-client").get())
    runtimeOnly(libs.findLibrary("spring-boot-starter-oauth2-resource-server").get())
    runtimeOnly(libs.findLibrary("spring-boot-starter-security").get())
    runtimeOnly(libs.findLibrary("spring-boot-starter-validation").get())
    runtimeOnly(libs.findLibrary("spring-boot-starter-web").get())

    testRuntimeOnly(libs.findLibrary("spring-boot-starter-validation").get())
    testImplementation(libs.findLibrary("spring-boot-test").get())
}

val dockerNamespace: String by project
tasks.named<BootBuildImage>("bootBuildImage") {
    dependsOn(tasks.named("build"))
    imageName = "${dockerNamespace}/${rootProject.name}/${project.name}:${project.version}"
}

configure<GraphfityPluginExtension> {
    nodeTypesPath = "${rootDir}/config/graphfityNodeTypes.json"
    projectRootName = project.path
    graphImagePath = layout.buildDirectory.get().asFile.absolutePath
}
