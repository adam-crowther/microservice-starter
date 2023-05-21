# Build and CI/CD

## Gradle Setup

We use Gradle Version 8.1.1, via the Gradle Wrapper.

Our Gradle build scripts are modularised using Convention Plugins: See
[Gradle: Sharing build logic between subprojects Sample](https://docs.gradle.org/current/samples/sample_convention_plugins.html)

Conventions are defined in scripts called `xxxx-conventions.gradle`, in the `buildSrc` submodule:

```
buildSrc
├── src
│   ├── main
│   │   └── groovy
│   │       ├── avro-conventions.gradle
│   │       ├── checkstyle-conventions.gradle
│   │       ├── cucumber-conventions.gradle
│   │       ├── gradlelint-conventions.gradle
│   │       ├── groovy-conventions.gradle
│   │       ├── jacoco-conventions.gradle
│   │       ├── java-conventions.gradle
│   │       ├── liquibase-conventions.gradle
│   │       ├── openapi-conventions.gradle
│   │       ├── pmd-conventions.gradle
│   │       ├── spock-conventions.gradle
│   │       ├── spotbugs-conventions.gradle
│   │       ├── spring-conventions.gradle
│   │       └── swagger-ui-conventions.gradle
│   └── test
│       └── groovy
│           ├── JavaConventionPluginTest.groovy
│           ├── ...
│           └── PluginTest.groovy
├── build.gradle
└── settings.gradle
```
Note that this counts as productive code.  We should at least make an effort to implement useful unit tests.

Each of these conventions configures Groovy for a given plugin, using our standard project settings. They are imported
in to the microservice `build.gradle` scripts like standard plugins:

````groovy
plugins {
  id 'java-conventions'
  id 'spock-conventions'
}

dependencies {
  implementation project(":common:common-helper")

  implementation 'org.apache.commons:commons-lang3'
  implementation 'com.google.guava:guava'
  implementation 'jakarta.annotation:jakarta.annotation-api'
  implementation 'org.projectlombok:lombok'

  annotationProcessor 'org.projectlombok:lombok'

  testAnnotationProcessor 'org.projectlombok:lombok'
} 
````

Most `build.gradle` scripts can be limited to a `plugins` block and a `dependencies` block, with everything else handled
by the Convention Plugins.

In a production project, when each microservice is given its own Git repository, these Convention Plugins will be
packaged and made available as normal Gradle plugins via a private build artifact repository, such as Nexus or
Artifactory. This serves to enforce our project standards.

### Java transitive dependencies

- The `java-conventions.gradle` Plugin enforces that transitive dependencies are not imported in to our project source
  code:
  <details>
  <summary>java-conventions.gradle: dependencies</summary>

  ```groovy
  configurations
        .matching({ name in ["implementation", "testImplementation"] })
        .configureEach({
          transitive = false
        })
  ```
  </details>
- It's mandatory for production software projects to keep dependencies under control. If we need a library we should
  make an active decision to use it. 
- We don't allow transitive dependencies, because they allow devs to use unsanctioned libraries by mistake.

### Java library / framework versions

- We enforce internal consistency of library versions through the `java-conventions.gradle` Plugin.
  <details>
  <summary>java-conventions.gradle: dependencies</summary>

  ```groovy
  // This defines all the dependency versions and ensures consistency
  dependencies {
    implementation enforcedPlatform('org.springframework.boot:spring-boot-dependencies:3.1.0')
    annotationProcessor enforcedPlatform('org.springframework.boot:spring-boot-dependencies:3.1.0')
    testAnnotationProcessor enforcedPlatform('org.springframework.boot:spring-boot-dependencies:3.1.0')
  
    testImplementation enforcedPlatform('io.cucumber:cucumber-bom:7.12.0')
  
    testImplementation enforcedPlatform('org.junit:junit-bom:5.9.3')
  
    constraints {
      implementation 'com.github.spotbugs:spotbugs-annotations:4.7.3'
      implementation 'com.google.guava:guava:31.1-jre'
      implementation 'io.confluent:kafka-avro-serializer:7.3.3'
      implementation 'io.confluent:kafka-schema-serializer:7.3.3'
      implementation 'io.swagger.core.v3:swagger-annotations:2.2.8'
      implementation 'io.swagger.core.v3:swagger-core:2.2.8'
      implementation 'jakarta.inject:jakarta.inject-api:2.0.1'
      implementation 'org.apache.avro:avro:1.11.0'
      implementation 'org.mapstruct:mapstruct:1.5.3.Final'
      implementation 'org.openapitools:jackson-databind-nullable:0.2.6'
      implementation 'org.yaml:snakeyaml:2.0'
  
      annotationProcessor 'io.swagger.core.v3:swagger-annotations:2.2.8'
      annotationProcessor 'org.mapstruct:mapstruct-processor:1.5.3.Final'
      annotationProcessor 'org.projectlombok:lombok-mapstruct-binding:0.2.0'
  
      runtimeOnly 'org.postgresql:postgresql:42.6.0'
  
      testImplementation 'junit:junit:4.13.2'
    }
  
    testImplementation 'com.github.npathai:hamcrest-optional:2.0.0'
    testImplementation 'nl.jqno.equalsverifier:equalsverifier:3.14.1'
    testImplementation 'org.awaitility:awaitility:4.2.0'
    testImplementation 'org.junit.jupiter:junit-jupiter'
    testImplementation 'org.mockito:mockito-core'
  }
  ```
  </details>
- Note that we use enforcedPlatform and Maven BOM files to ensure that the versions that we use are internally
  consistent. See
  [MVNRepository: Spring Boot Dependencies » 3.1.0](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-dependencies/3.1.0)
  with its 560 "Managed Dependencies".
- An "enforcedPlatform" dependency treats the versions in the bom as requirements and they will override any other
  version found in the dependency graph. See
  [Spring Boot Gradle Plugin Reference Guide: Managing Dependencies with Gradle’s Bom Support](https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/htmlsingle/#managing-dependencies.gradle-bom-support)
- Microservice build.gradle scripts **must not** define versions of library dependencies. Note that there are no
  versions defined here:
  ````groovy
  dependencies {
    implementation project(":common:common-helper")
  
    implementation 'org.apache.commons:commons-lang3'
    implementation 'com.google.guava:guava'
    implementation 'jakarta.annotation:jakarta.annotation-api'
    implementation 'org.projectlombok:lombok'
  
    annotationProcessor 'org.projectlombok:lombok'
  
    testAnnotationProcessor 'org.projectlombok:lombok'
  } 
  ````

## Gradle Tasks

### Basic tasks

|         | Gradle command               | Task dependencies                   |
|---------|------------------------------|-------------------------------------|
| Clean   | `gradle clean`               | -                                   |
| Compile | `gradle classes testClasses` | -                                   |
| Package | `gradle jar`                 | Compile                             |
| Test    | `gradle test`                | Compile                             |
| Check   | `gradle check`               | Compile, Static code analysis, Test |
| Build   | `gradle build`               | Pompile, Package, Check             |

### OpenApi

2 tasks: validate the openapi.yaml file, and generate the stubs and skeletons in to: `build/generated/src/main/java`.
This target directory is included in the sourceSet for the submodules that depend on the generated code.

|          | Gradle command           | Task dependencies |
|----------|--------------------------|-------------------|
| Validate | `gradle openApiValidate` | -                 |
| Generate | `gradle openApiGenerate` | Compile           |

### Liquibase

Here we use 2 tasks:

- Generate a full Changelog from the JPA entity notations, containing the full database schema as a YAML file in the
  Liquibase database-agnostic
  notation.
- Generate a DIFF Changelog, comparing the JPA entity notations with the current Changelog in the `resources` folder.

Changelogs are generated to `build/liquibase/`, and are only intended to serve as a starting point for database
migration scripts. They need to be checked for correctness and updated manually to meet coding standards before being
integrated in the `resources` folder.

|                    | Gradle command                      | Task dependencies            |
|--------------------|:------------------------------------|------------------------------|
| Generate Changelog | `gradle liquibaseGenerateChangelog` | Compile, Package             |
| Diff Changelog     | `gradle liquibaseDiffChangelog`     | Compile, Package, Compose Up |

Uses Docker Compose to start up a clean PostgreSQL database.

### Static code analysis

- All static code analysis tasks are triggered by the `gradle check` task.
- All failures should break the build and prevent PRs from being merged.

#### Checkstyle

- Our code style is loosely based on the Google style, with some modifications to make it more readable, more lenient
  and easier to conform.
- See https://checkstyle.sourceforge.io/
- Checkstyle is configured in the file: [checkstyle.xml](../config/checkstyle.xml)
- Please load the Intellij [Intellij_codestyle_java.xml](../config/Intellij_codestyle_java.xml), which will implement
  the rules that are enforced by Checkstyle.

|                  | Gradle command          | Task dependencies |
|------------------|:------------------------|-------------------|
| Analyse src/main | `gradle checkstyleMain` | Compile           |
| Analyse src/test | `gradle checkstyleTest` | Compile           |

#### PMD

- Static code analyser that finds common simple flaws, like unused variables, empty catch blocks, unnecessary object
  creation, etc.
- See https://docs.pmd-code.org/latest/
- PMD is configured in the file: [pmd_rules.xml](../config/pmd_rules.xml)
- Configure `Actions On Save` in Intellij to enforce our coding standards, as follows:
  [![img.png](images/intellij-actions-on-save-thumb.png)](./images/intellij-actions-on-save.png)

|                  | Gradle command   | Task dependencies |
|------------------|:-----------------|-------------------|
| Analyse src/main | `gradle pmdMain` | Compile           |
| Analyse src/test | `gradle pmdTest` | Compile           |

#### Spotbugs

- Static code analyser that looks for common "bug patterns":
    - Bad practices
    - Coding mistakes
    - Malicious code
    - Random noise
    - Performance issues
    - Security issues
    - Confusing code.
- See https://spotbugs.readthedocs.io/en/latest/
- Spotbugs is configured in the exclusion file: [spotbugs_exclude.xml](../config/spotbugs_exclude.xml)

|                  | Gradle command        | Task dependencies |
|------------------|:----------------------|-------------------|
| Analyse src/main | `gradle spotbugsMain` | Compile           |
| Analyse src/test | `gradle spotbugsTest` | Compile           |

#### Codenarc

- Static code analysis for Groovy Scripts.
- We have it configured to allow the conventions that are commonly used by Spock specifications.
- See https://codenarc.org/
- Codenarc is configured in the config file: [codenarc.xml](../config/codenarc.xml)

#### JacCoCo

- Test coverage: reporting and enforcement
- See https://www.eclemma.org/jacoco/
- The plugin is configured to fail the build if coverage is less than 95%.
- Reports are generated in `build/reports/jacoco`
- JaCoCo is configured in the file: [jacoco.gradle](../config/jacoco.gradle)

|                          | Gradle command            | Task dependencies |
|--------------------------|:--------------------------|-------------------|
| Generate the HTML report | `gradle jacocoTestReport` | Compile Test      |

#### Gradle Lint

- Gradle Lint checks the gradle setup for wrong configuration, deprecation and unused dependencies.
- Unused dependencies rule is currently disabled due to false positives.
- See https://github.com/nebula-plugins/gradle-lint-plugin/wiki
- We only run gradleLint as a dependency of the check task, not for every build.
- Use the `generateGradleLintReport` task to find which submodule is generating a lint error.
- Do NOT run `fixGradleLint` to apply fix changes automatically.
- GradleLint is configured directly in the gradle build file: [gradlelint.gradle](../config/gradlelint.gradle)

|                  | Gradle command                    | Task dependencies |
|------------------|:----------------------------------|-------------------|
| Run Lint         | `gradle gradleLint`               | -                 |
| Generate Reports | `gradle generateGradleLintReport` | -                 |

### Graphfity

- Graphfity is a tool that generated dependency diagrams like this one:
  [![order-container.png](images/order-container-thumb.png)](./images/order-container.png)
- See https://github.com/ivancarras/graphfity
- It generates a PNG in the container's build folder: `build/project.dot.png`
- Graphfity is configured in each container submodule, in the file
  [graphfityNodeTypes.json](../customer-service/customer-container/graphfityNodeTypes.json).

|                                       | Gradle command                                          | Task dependencies |
|---------------------------------------|:--------------------------------------------------------|-------------------|
| Generate graph for Airline Container  | `gradle :airline-service:airline-container:graphfity`   | -                 |
| Generate graph for Customer Container | `gradle :customer-service:customer-container:graphfity` | -                 |
| Generate graph for Order Container    | `gradle :order-service:order-container:graphfity`       | -                 |
| Generate graph for Payment Container  | `gradle :payment-service:payment-container:graphfity`   | -                 |

### Spring Boot

- Spring Boot Gradle plugin, providing multiple capabilities:
    - Packaging executable JAR archives that can be run with java -jar
    - Run the application locally
    - Publish the application to a Maven Repository like Nexus or Artifactory
    - Create a Docker image containing the Spring application (we don't use this)
    - Dependency Management (we don't use this)
- See https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/htmlsingle/

|                                | Gradle command                                        | Task dependencies |
|--------------------------------|:------------------------------------------------------|-------------------|
| Package the Airline Container  | `gradle :airline-service:airline-container:bootJar`   | -                 |
| Run the Airline Container      | `gradle :airline-service:airline-container:bootRun`   | -                 |
| Package the Customer Container | `gradle :customer-service:customer-container:bootJar` | -                 |
| Run the Customer Container     | `gradle :customer-service:customer-container:bootRun` | -                 |
| Package the Order Container    | `gradle :order-service:order-container:bootJar`       | -                 |
| Run the Order Container        | `gradle :order-service:order-container:bootRun`       | -                 |
| Package the Payment Container  | `gradle :payment-service:payment-container:bootJar`   | -                 |
| Run the Payment Container      | `gradle :payment-service:payment-container:bootRun`   | -                 |
