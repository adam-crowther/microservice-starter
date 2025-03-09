import com.github.spotbugs.snom.SpotBugsTask

plugins {
  id("com.github.spotbugs")
}

val libs = versionCatalogs.named("libs")

dependencies {
  spotbugsPlugins(libs.findLibrary("spotbugs-findsecbugs").get())
}

spotbugs {
  toolVersion = "4.9.0"
  ignoreFailures = false
  showStackTraces = true
  showProgress = false
  effort = com.github.spotbugs.snom.Effort.MAX
  reportLevel = com.github.spotbugs.snom.Confidence.LOW
  visitors = listOf("FindSqlInjection", "SwitchFallthrough")
  omitVisitors = listOf("FindNonShortCircuit")
  reportsDir = layout.buildDirectory.file("tmp/spotbugs/").get().asFile
  // includeFilter = file("include.xml")
  excludeFilter = file("${rootDir}/config/spotbugs_exclude.xml")
  // baselineFile = file("baseline.xml")
  // onlyAnalyze = listOf("com.foobar.MyClass", "com.foobar.mypkg.*")
  maxHeapSize = "1g"
  extraArgs = listOf("-nested:false")
}

tasks.named<SpotBugsTask>("spotbugsMain") {
  reports.create("html") {
    required = true
    outputLocation = layout.buildDirectory.file("reports/spotbugs/spotbugsMain.html")
    analyseClassFile = layout.buildDirectory.file("tmp/spotbugs/spotbugsMainFixtures-analyse-class-file.txt")
    auxclasspathFile = layout.buildDirectory.file("tmp/spotbugs/spotbugsMainFixtures-auxclasspath-file.txt")
    setStylesheet("fancy-hist.xsl")
  }
}
tasks.named<SpotBugsTask>("spotbugsTest") {
  reports.create("html") {
    required = true
    outputLocation = layout.buildDirectory.file("reports/spotbugs/spotbugsTest.html")
    analyseClassFile = layout.buildDirectory.file("tmp/spotbugs/spotbugsTest-analyse-class-file.txt")
    auxclasspathFile = layout.buildDirectory.file("tmp/spotbugs/spotbugsTest-auxclasspath-file.txt")
    setStylesheet("fancy-hist.xsl")
  }
}
tasks.named<SpotBugsTask>("spotbugsTestFixtures") {
  reports.create("html") {
    required = true
    outputLocation = layout.buildDirectory.file("reports/spotbugs/spotbugsTestFixtures.html")
    analyseClassFile = layout.buildDirectory.file("tmp/spotbugs/spotbugsTestFixtures-analyse-class-file.txt")
    auxclasspathFile = layout.buildDirectory.file("tmp/spotbugs/spotbugsTestFixtures-auxclasspath-file.txt")
    setStylesheet("fancy-hist.xsl")
  }
}