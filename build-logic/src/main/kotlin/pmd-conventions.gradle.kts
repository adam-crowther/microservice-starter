plugins {
    pmd
}

pmd {
    toolVersion = "7.9.0"
    setConsoleOutput(true)
    rulesMinimumPriority = 5
    ruleSets = emptyList()
    ruleSetFiles = files("${rootDir}/config/pmd_rules.xml")
    maxFailures = 0
    isIgnoreFailures = false
    incrementalAnalysis = true
}

tasks.named("pmdMain") {
    group = "verification"
}

tasks.named("pmdTest") {
    group = "verification"
}

tasks.named("pmdTestFixtures") {
    group = "verification"
}