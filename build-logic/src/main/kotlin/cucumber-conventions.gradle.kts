plugins {
  java
}

val libs = versionCatalogs.named("libs")

dependencies {
  testImplementation(libs.findLibrary("cucumber-java").get())
  testImplementation(libs.findLibrary("cucumber-junit").get())
  testImplementation(libs.findLibrary("cucumber-picocontainer").get())
  testImplementation(libs.findLibrary("junit").get())
  testImplementation(libs.findLibrary("junit-vintage").get())
}