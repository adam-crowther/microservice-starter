plugins {
    java
    jacoco
}

jacoco {
    toolVersion = "0.8.12"
}

tasks.named<JacocoReport>("jacocoTestReport") {
    dependsOn(tasks.named("test"))
    reports {
        xml.required = false
        csv.required = false
        html.required = true
        html.outputLocation = layout.buildDirectory.dir("reports/jacoco")
    }
    classDirectories = files(classDirectories.files.map {
        fileTree(it) {
            exclude("**/config/*Configuration.class", "**/properties/*Config.class")
        }
    })
}

tasks.named<Test>("test") {
    configure<JacocoTaskExtension> {
        isEnabled = true
        // TODO
        setDestinationFile(layout.buildDirectory.file("tmp/jacoco/${name}.exec").get().asFile)
        isIncludeNoLocationClasses = false
        isDumpOnExit = true
        classDumpDir = null
        output = JacocoTaskExtension.Output.FILE
        // address = "localhost"
        // port = 6300
        isJmx = false
    }

    finalizedBy(tasks.named("jacocoTestReport"))
}

tasks.named<JacocoCoverageVerification>("jacocoTestCoverageVerification") {
    violationRules {
        isFailOnViolation = false
        rule {
            limit {
                minimum = "0.95".toBigDecimal()
            }
        }

        rule {
            enabled = true
            element = "CLASS"
            includes = listOf("ch.acroteq.ticketing.*")

            limit {
                counter = "LINE"
                value = "TOTALCOUNT"
                minimum = "0.95".toBigDecimal()
            }
        }
    }
    classDirectories = files(classDirectories.files.map {
        fileTree(it) {
            exclude("**/config/*Configuration.class", "**/properties/*Config.class")
        }
    })
}

tasks.named("test") {
    finalizedBy(tasks.named("jacocoTestReport"), tasks.named("jacocoTestCoverageVerification"))
}
