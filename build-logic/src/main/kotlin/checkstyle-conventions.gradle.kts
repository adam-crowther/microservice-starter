plugins {
    java
    checkstyle
}

checkstyle {
    toolVersion = "10.21.2"
}

tasks.named<Checkstyle>("checkstyleMain") {
    group = "verification"
    configFile = file("${rootDir}/config/checkstyle_main.xml")
    maxWarnings = 0
    maxErrors = 0
}

tasks.named<Checkstyle>("checkstyleTest") {
    group = "verification"
    configFile = file("${rootDir}/config/checkstyle_test.xml")
    @Suppress("UnstableApiUsage")
    ignoreFailures = true
}

tasks.named<Checkstyle>("checkstyleTestFixtures") {
    group = "verification"
    configFile = file("${rootDir}/config/checkstyle_test.xml")
    @Suppress("UnstableApiUsage")
    ignoreFailures = true
}

tasks.withType<Checkstyle>().configureEach {
    outputs.upToDateWhen { false }
    exclude("**/presentation/api/**")
    exclude("**/presentation/model/**")
    exclude("**/client/api/**")
    exclude("**/client/model/**")
    exclude("**/client/invoker/**")
    exclude("**/avro/model/**")
}
