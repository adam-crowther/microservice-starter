import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

plugins {
    java
    id("org.liquibase.gradle")
    id("com.avast.gradle.docker-compose")
}

configurations.named("liquibaseRuntime") {
    extendsFrom(configurations["compileClasspath"])
    exclude(group = "ch.qos.logback")
    exclude(group = "org.apache.logging.log4j")
    exclude(group = "org.slf4j", module = "slf4j-reload4j")
}

configurations.named("compileOnly") {
    extendsFrom(configurations["annotationProcessor"])
}

val libs = versionCatalogs.named("libs")

dependencies {
    constraints {
        liquibaseRuntime(group = "org.liquibase", name = "liquibase-groovy-dsl", version = "4.0.0") {
            exclude(group = "org.codehaus.groovy", module = "groovy")
            exclude(group = "org.codehaus.groovy", module = "groovy-sql")
        }
    }

    liquibaseRuntime(libs.findLibrary("liquibase-groovy-dsl").get())
    liquibaseRuntime(libs.findLibrary("jakarta-annotation-api").get())
    liquibaseRuntime(libs.findLibrary("liquibase-core").get())
    liquibaseRuntime(libs.findLibrary("liquibase-hibernate6").get())
    liquibaseRuntime(libs.findLibrary("picocli").get())
    liquibaseRuntime(libs.findLibrary("postgresql").get())
    liquibaseRuntime(libs.findLibrary("slf4j-simple").get())
    liquibaseRuntime(libs.findLibrary("snakeyaml").get())
    liquibaseRuntime(libs.findLibrary("spring-boot-starter-data-jpa").get())

    runtimeOnly(libs.findLibrary("liquibase-core").get())
    runtimeOnly(libs.findLibrary("liquibase-hibernate6").get())
    runtimeOnly(libs.findLibrary("liquibase-slf4j").get())
    runtimeOnly(libs.findLibrary("postgresql").get())

    liquibaseRuntime(sourceSets.main.get().output)
}

dockerCompose {
    useComposeFiles = listOf("docker-compose.yaml")
    startedServices = listOf("postgres") //, "pgadmin")
    dockerComposeWorkingDirectory = layout.projectDirectory
}

tasks.named("composeUp") {
    finalizedBy(tasks.named("composeDown"))
    doLast {
        val postgresqlPort = dockerCompose.servicesInfos["postgres"]?.ports?.get(5432)
        project.extensions.extraProperties["postgresqlPort"] = postgresqlPort
    }
}

tasks.register("liquibaseInitialise") {
    doFirst {
        mkdir(layout.buildDirectory.dir("liquibase").get())

        val timestamp = DateTimeFormatter
            .ofPattern("yyyyMMdd-HHmmss")
            .withZone(ZoneId.systemDefault())
            .format(Instant.now())

        // Configure Liquibase using project extensions
        project.extensions.extraProperties["timestamp"] = timestamp

        liquibase {
            setJvmArgs("-Duser.dir=" + layout.projectDirectory)
        }
    }
}

tasks.named("liquibaseDropAll") {
    dependsOn(tasks.named("liquibaseInitialise"))
    dependsOn(tasks.named("composeUp"))
    finalizedBy(tasks.named("composeDown"))

    doFirst {
        // Ensure liquibase extension is configured properly
        liquibase {
            activities.register("dropSchema") {
                arguments = mapOf(
                    "url" to "jdbc:postgresql://localhost:${project.extra["postgresqlPort"]}/postgres",
                    "driver" to "org.postgresql.Driver",
                    "username" to "postgres",
                    "password" to "password",
                    "logFile" to layout.buildDirectory.file("liquibase/liquibase_dropall.log").get(),
                    "logLevel" to "FINE",
                    "format" to "yaml",
                    "showBanner" to "false",
                    "convertDataTypes" to "true",
                    "diffColumnOrder" to "false",
                )

            }
            runList = "dropSchema"
        }
    }
}

tasks.named("liquibaseUpdate") {
    // Start up Postgres docker container before running liquibaseDiffChangelog and shut it down after
    dependsOn(tasks.named("liquibaseInitialise"))
    dependsOn(tasks.named("liquibaseDropAll"))
    dependsOn(tasks.named("composeUp"))
    finalizedBy(tasks.named("composeDown"))

    doFirst {
        liquibase {
            activities.register("deployChangeLog") {
                arguments = mapOf(
//                    "changelogFile" to layout.projectDirectory.file("src/main/resources/db/changelog/changelog-master.yaml"), // .asFile.absolutePath,
                    "changelogFile" to "src/main/resources/db/changelog/changelog-master.yaml",
                    "url" to "jdbc:postgresql://localhost:${project.extra["postgresqlPort"]}/postgres",
                    "driver" to "org.postgresql.Driver",
                    "username" to "postgres",
                    "password" to "password",
                    "logFile" to layout.buildDirectory.file("liquibase/liquibase_update.log").get(),
                    "logLevel" to "FINE",
                    "format" to "yaml",
                    "showBanner" to "false",
                    "convertDataTypes" to "true",
                    "diffColumnOrder" to "false",
                )
            }
            runList = "deployChangeLog"
        }
    }
}

tasks.named("liquibaseDiffChangelog") {
    dependsOn(tasks.named("liquibaseInitialise"))
    dependsOn(tasks.named("liquibaseUpdate"))
    dependsOn(tasks.named("compileJava"))
    finalizedBy(tasks.named("composeDown"))

    doFirst {
        // Ensure liquibase extension is configured properly
        liquibase {
            activities.register("generateDiffChangeLog") {
                arguments = mapOf(
                    "changelogFile" to layout.buildDirectory.file("liquibase/changelog-diff-${project.extra["timestamp"]}.yaml")
                        .get(),
                    "overwriteOutputFile" to true,
                    "url" to "jdbc:postgresql://localhost:${project.extra["postgresqlPort"]}/postgres",
                    "driver" to "org.postgresql.Driver",
                    "username" to "postgres",
                    "password" to "password",
                    "referenceUrl" to "hibernate:spring:com.acroteq.ticketing?dialect=org.hibernate.dialect.PostgreSQLDialect&" +
                            "hibernate.physical_naming_strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl&" +
                            "hibernate.implicit_naming_strategy=org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy",
                    "referenceDriver" to "liquibase.ext.hibernate.database.connection.HibernateDriver",
                    "logFile" to layout.buildDirectory.file("liquibase/liquibase_diff.log").get(),
                    "format" to "yaml",
                    "logLevel" to "FINE",
                    "showBanner" to "false",
                    "convertDataTypes" to "true",
                    "diffColumnOrder" to "false",
                )
            }
            runList = "generateDiffChangeLog"
        }
    }
}

tasks.named("liquibaseGenerateChangelog") {
    dependsOn(tasks.named("liquibaseInitialise"))
    dependsOn(tasks.named("compileJava"))

    liquibase {
        activities.register("generateChangeLog") {
            arguments = mapOf(
                "changelogFile" to layout.buildDirectory.file("liquibase/changelog-${project.extra["timestamp"]}.yaml")
                    .get(),
                "overwriteOutputFile" to true,
                "url" to "hibernate:spring:com.acroteq.ticketing?dialect=org.hibernate.dialect.PostgreSQLDialect&" +
                        "hibernate.physical_naming_strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl&" +
                        "hibernate.implicit_naming_strategy=org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy",
                "driver" to "liquibase.ext.hibernate.database.connection.HibernateDriver",
                "logFile" to layout.buildDirectory.file("liquibase/liquibase_gen.log").get(),
                "format" to "yaml",
                "logLevel" to "FINE",
                "showBanner" to "false",
                "convertDataTypes" to "true",
                "diffColumnOrder" to "false",
            )
        }
        runList = "generateChangeLog"
    }
}
