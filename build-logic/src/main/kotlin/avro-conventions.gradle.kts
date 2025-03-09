plugins {
  java
  id("java-conventions")
  id("com.github.davidmc24.gradle.plugin.avro")
}

val libs = versionCatalogs.named("libs")

dependencies {
  implementation(libs.findLibrary("avro").get())
  implementation(libs.findLibrary("guava").get())
  implementation(libs.findLibrary("jackson-core").get())
  implementation(libs.findLibrary("kafka-avro-serializer").get())
  implementation(libs.findLibrary("kafka-clients").get())
}

avro {
  isCreateSetters = false
  isGettersReturnOptional = true
  isOptionalGettersForNullableFieldsOnly = true
  stringType = "String"
  isEnableDecimalLogicalType = true
  fieldVisibility = "PRIVATE"
}
