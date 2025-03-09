plugins {
  id("groovy-conventions")
}

val libs = versionCatalogs.named("libs")

dependencies {
  testImplementation(platform(libs.findLibrary("spock-bom").get()))

  testImplementation(libs.findLibrary("spock-core").get())
  testImplementation(libs.findLibrary("byte-buddy").get())
  testImplementation(libs.findLibrary("objenesis").get())
  testImplementation(libs.findLibrary("hamcrest").get())

  testRuntimeOnly(libs.findLibrary("junit-platform-launcher").get())
}
