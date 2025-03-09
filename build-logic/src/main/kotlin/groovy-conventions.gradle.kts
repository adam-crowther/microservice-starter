plugins {
    groovy
    codenarc
}

codenarc {
    configFile = file("${rootDir}/config/codenarc.xml")
    reportFormat = "html"
    reportsDir = layout.buildDirectory.dir("reports/codenarc").get().asFile
    isIgnoreFailures = false
    maxPriority1Violations = 0
    maxPriority2Violations = 0
    maxPriority3Violations = 0
}

tasks.named<CodeNarc>("codenarcMain") {
    group = "verification"
    compilationClasspath = sourceSets.main.get().compileClasspath + sourceSets.main.get().output
    configFile = file("${rootDir}/config/codenarc.xml")
    reports {
        html.apply {
            required = true
            outputLocation = layout.buildDirectory.dir("reports/codenarc").get().asFile
        }
        text.required = false
    }
    maxPriority1Violations = 0
    maxPriority2Violations = 0
    maxPriority3Violations = 0
}

tasks.named<CodeNarc>("codenarcTest") {
    group = "verification"
    compilationClasspath =
        sourceSets.main.get().compileClasspath +
                sourceSets.main.get().output +
                sourceSets.test.get().compileClasspath +
                sourceSets.test.get().output
    configFile = file("${rootDir}/config/codenarc.xml")
    reports {
        html.apply {
            required = true
            outputLocation = layout.buildDirectory.dir("reports/codenarc").get().asFile
        }
        text.required = false
    }
    @Suppress("UnstableApiUsage")
    ignoreFailures = true
    maxPriority1Violations = 0
    maxPriority2Violations = 0
    maxPriority3Violations = 0
}

tasks.named("compileGroovy") {
    group = "build"
}

tasks.named("compileTestGroovy") {
    group = "build"
}
