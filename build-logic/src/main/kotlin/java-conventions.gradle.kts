plugins {
    java
    `java-library`
    `java-test-fixtures`
    id("checkstyle-conventions")
    id("jacoco-conventions")
    id("pmd-conventions")
    id("spotbugs-conventions")
}

repositories {
    mavenCentral()
}

val libs = versionCatalogs.named("libs")

// This defines all the dependency versions and ensures consistency
dependencies {
    val springBootBom = libs.findLibrary("spring-boot-bom").get()

    implementation(enforcedPlatform(springBootBom))
    annotationProcessor(enforcedPlatform(springBootBom))
    testFixturesImplementation(enforcedPlatform(springBootBom))
    testAnnotationProcessor(enforcedPlatform(springBootBom))
    testFixturesAnnotationProcessor(enforcedPlatform(springBootBom))

    testImplementation(enforcedPlatform(libs.findLibrary("cucumber-bom").get()))
    testImplementation(enforcedPlatform(libs.findLibrary("junit-bom").get()))

    constraints {
        annotationProcessor(libs.findLibrary("swagger-annotations").get())
        annotationProcessor(libs.findLibrary("mapstruct-processor").get())
        annotationProcessor(libs.findLibrary("lombok").get())
        annotationProcessor(libs.findLibrary("lombok-mapstruct-binding").get())

        implementation(libs.findLibrary("spotbugs-annotations").get())
        implementation(libs.findLibrary("guava").get())
        implementation(libs.findLibrary("kafka-avro-serializer").get())
        implementation(libs.findLibrary("kafka-schema-serializer").get())
        implementation(libs.findLibrary("swagger-annotations").get())
        implementation(libs.findLibrary("swagger-core").get())
        implementation(libs.findLibrary("jakarta.inject-api").get())
        implementation(libs.findLibrary("avro").get())
        implementation(libs.findLibrary("commons-math3").get())
        implementation(libs.findLibrary("mapstruct").get())
        implementation(libs.findLibrary("jackson-databind-nullable").get())
        implementation(libs.findLibrary("lombok").get())
        implementation(libs.findLibrary("snakeyaml").get())

        testImplementation(libs.findLibrary("junit").get())

        testFixturesAnnotationProcessor(libs.findLibrary("swagger-annotations").get())
        testFixturesAnnotationProcessor(libs.findLibrary("mapstruct-processor").get())
        testFixturesAnnotationProcessor(libs.findLibrary("lombok-mapstruct-binding").get())
        testFixturesImplementation(libs.findLibrary("spotbugs-annotations").get())
        testFixturesImplementation(libs.findLibrary("guava").get())
        testFixturesImplementation(libs.findLibrary("kafka-avro-serializer").get())
        testFixturesImplementation(libs.findLibrary("kafka-schema-serializer").get())
        testFixturesImplementation(libs.findLibrary("swagger-annotations").get())
        testFixturesImplementation(libs.findLibrary("swagger-core").get())
        testFixturesImplementation(libs.findLibrary("jakarta.inject-api").get())
        testFixturesImplementation(libs.findLibrary("avro").get())
        testFixturesImplementation(libs.findLibrary("commons-math3").get())
        testFixturesImplementation(libs.findLibrary("mapstruct").get())
        testFixturesImplementation(libs.findLibrary("jackson-databind-nullable").get())
        testFixturesImplementation(libs.findLibrary("snakeyaml").get())

        runtimeOnly(libs.findLibrary("postgresql").get())
    }

    testImplementation(libs.findLibrary("awaitility").get())
    testImplementation(libs.findLibrary("equalsverifier").get())
    testImplementation(libs.findLibrary("hamcrest").get())
    testImplementation(libs.findLibrary("hamcrest-optional").get())
    testImplementation(libs.findLibrary("junit-jupiter").get())
    testImplementation(libs.findLibrary("mockito-core").get())

    testFixturesImplementation(libs.findLibrary("awaitility").get())
    testFixturesImplementation(libs.findLibrary("equalsverifier").get())
    testFixturesImplementation(libs.findLibrary("hamcrest").get())
    testFixturesImplementation(libs.findLibrary("hamcrest-optional").get())
    testFixturesImplementation(libs.findLibrary("junit-jupiter").get())
    testFixturesImplementation(libs.findLibrary("mockito-core").get())

    testRuntimeOnly(libs.findLibrary("junit-platform-launcher").get())
}

// It's mandatory for production software projects to keep dependencies under control.  If we need a library we should
// make an active decision to use it.  We don't allow transitive dependencies in our code, because they allow devs to
// use unsanctioned libraries.
configurations
    .matching({ name in listOf("implementation", "testImplementation") })
    .configureEach({
        isTransitive = false
    })

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(Integer.valueOf(System.getProperty("javaSourceCompatibility") ?: "17"))
    }
}

tasks.named<JavaCompile>("compileJava") {
    group = "build"
    options.compilerArgs.addAll(listOf("-Xlint:all,-serial,-processing", "-parameters"))
}

tasks.named<JavaCompile>("compileTestJava") {
    group = "build"
}

tasks.named<JavaCompile>("compileTestFixturesJava") {
    group = "build"
}

tasks.named<Test>("test") {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}