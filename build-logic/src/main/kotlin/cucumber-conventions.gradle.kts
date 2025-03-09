plugins {
  java
}

val libs = versionCatalogs.named("libs")

dependencies {
  testImplementation(libs.findLibrary("cucumber-java"))
  testImplementation(libs.findLibrary("cucumber-junit"))
  testImplementation(libs.findLibrary("cucumber-picocontainer"))
  testImplementation(libs.findLibrary("junit"))
  testImplementation(libs.findLibrary("junit-vintage"))
}