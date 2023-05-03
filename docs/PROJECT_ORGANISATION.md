# Project Organisation

## `common` submodules

- A set of common submodules containing code that is shared by multiple microservices.
- There must be no business logic and no model here
  ### `common-application`
    - Contains shared code that is used by the `xxxx-application` submodules of the microservices.
    - Must not contain business logic or models.
  ### `common-domain`
    - Contains shared code that is used by the `xxxx-domain` submodules of the microservices.
    - Must not contain business logic or models.
  ### `common-helper`
    - Contains very basic shared code that can be used anywhere in the application.
    - Along the lines of Apache Commons or Google Guava
  ### `common-infrastructure`
    - Contains shared code that is used by the `xxx-infrastructure` submodules of the microservices.
    - Must not contain business logic or models.
  ### `common-presentation`
    - Contains shared code that is used by the `xxxx-presentation` submodules of the microservices. Mainly mapping and
      resolvers.
    - Must not contain business logic or models.
  ### `common-kafka`
    - Contains 2 submodules
      #### `kafka-libs`
        - Kafka libraries, wrapping the Spring/Confluent libraries
      #### `kafka-model`
        - Apache Avro definitions for the messages that will be sent to Kafka.
        - These message definitions are located outside the microservice submodules, because each will be used by at
          least 2 microservices.
        - It is likely that this will be split into multiple independent submodules, one per DDD bounded context.
  ### `common-saga`
    - Supporting libraries and code that will be used by the microservices that implement Saga pattern.

## Microservice submodules

- Each microservice is located in its own submodule,
  eg `airline-mdm`, `customer-mdm`, `order-service`, `airline-approval-service`,  `payment-service`.
- MDM is an industry standard abbreviation, and stands for 'Master Data Management'.
- In a real-world project, each microservice would be split out in to its own Git Repository, and integrated via Maven
  Artifacts.
- Microservice code can *only* depend on the `common` submodules
- A microservice submodule is divided into submodules, one for each layer of the DDD architecture:

  ### `xxxx-domain` submodule
    - Contains the Business Domain model and logic.
    - Can only depend on `common-domain`.
    - *Has no dependencies on any other submodules of the microservice*.
    - Contains 2 submodules:
      #### `xxxx-domain-core`
        - The Domain core contains **all** the business model and **all** the business logic.
            - Entity model
            - Event model
            - Value Objects
            - Domain Services
        - The Domain Entity model is defined in terms of entities and their relationships, and it implements the
          business logic.
        - Where business logic is limited to a single DDD Aggregate, it should be implemented as methods in the Entity
          model.
        - Domain Services are used when business logic requires multiple DDD Aggregates to work together.
        - It must be _completely independent of other project submodules or external frameworks_.
        - It must contain no integration code:  no database and no messaging infrastructure integration.
        - We accept a very limited number of external library dependencies, where they help to achieve clean code and
          reduce boilerplate:
            - Slf4j
            - Lombok
            - Google Guava
            - Apache Commons Lang
      #### `xxxx-application-service`
        - The application service submodule implements the DDD layer of the same name, and forms part of the wider
          `xxx-domain` layer
        - It provides the Application Logic implementation in the form of services and/or listeners.
        - Application logic is code that can call multiple business services, and uses the Spring as its integration
          framework, for integration injection, transactions and validation.
        - The Domain Layer's external API is defined as a set of Input- and Output-Port definitions (as defined by
          Hexagonal Architecture):
            - Input ports can be
                - Service interfaces, for synchronous invocations (e.g. when called by a REST API)
                - Message Listeners, for asynchronous invocations (e.g. from Kafka or ActiveMQ)
            - Output ports can be
                - Repositories, for data persistence (e.g. to an RDB such as Postgres, or to a NoSQL-DB)
                - Message Publishers, for asynchronous responses or events (e.g. to Kafka or ActiveMQ)
            - These ports are purely defined as _Java interfaces_. In the running application, Spring IOC will be used
              to provide the implementations by injection.
            - External input and output ports must **not** expose the internal Domain model to the outside. Instead, we
              define a DTO (=Data Transfer Object) model for that purpose.
            - The DTO model is used in the external interface (Input and Output Ports), and is defined
              in terms of interactions:  Queries (Request e.g.
              [TrackOrderQueryDto](../order-service/order-domain/order-application-service/src/main/java/com/acroteq/ticketing/order/service/domain/dto/track/TrackOrderQueryDto.java)
              and Response
              [TrackOrderResponseDto](../order-service/order-domain/order-application-service/src/main/java/com/acroteq/ticketing/order/service/domain/dto/track/TrackOrderResponseDto.java)
              ) and Commands (e.g.
              [CreateOrderCommandDto](../order-service/order-domain/order-application-service/src/main/java/com/acroteq/ticketing/order/service/domain/dto/create/CreateOrderCommandDto.java)
              ).

  ### `xxxx-presentation`
    - This submodule is only needed if the microservice presents a REST interface for communication with a UI over the
      web.
    - It implements the classic 'Application Layer' of a web-application: the top layer of the backend stack, which
      communicates with the frontend.
    - This submodule corresponds to the 'User Interface' layer that is shown in yellow at the top of the DDD
      architecture diagram at the top of this document.
    - Contains the REST API spec and REST-Controller modules for the microservice.
      #### `xxxx-api-spec`
        - Contains the `openapi.yaml` file, which defines the REST API model and end-points.
        - The build integration uses the OpenApiTools code generator to provide the required model, stubs and skeleton
          classes to whoever needs them.
        - The spec is split out in to its own submodule to allow it to be uploaded to a Maven repository (e.g.
          Artifactory or Nexus) separately, for implementation by third parties.
        - See [below: "REST API design / OpenApi"](#openapi) for more details.
      #### `xxxx-rest-controller`
        - Implements the REST API that is defined in the neighbouring `xxx-api-spec` submodule.
        - The REST API is implemented as a Spring `@RestController`.
        - The controller implementation converts the external API model to the internal DTO model, which is used to
          invoke the application service. (We do **not** want the external API model bleeding in to the Domain layer.)
        - The application service implementation is injected in to the REST controller. (No inversion of control here).
        - The REST API is configured on port 818x.
      #### `xxxx-swagger`
        - Auto-generates a Swagger UI that delegates to the `xxxx-rest-controller` microservice.
        - The Swagger UI is a pure client-side Javascript application, that is served by its own Spring Application.
        - For more information see [REST_API.md](REST_API.md#swagger-ui)

### `xxxx-infrastructure`

- Provides the implementations for all Input and Output Ports except for the REST API, which is provided by
  the `xxxx-application` submodule.
- Implementations are injected into the Domain Application-Service layer using Spring IOC.
- By definition, implementations are specific to and specialised for the backend applications that they
  integrate, e.g. PostgreSQL and Kafka. The abstract, implementation-agnostic interfaces are defined by the
  Input and Output Ports.
  #### `xxxx-data-access`
    - Contains the database schema definition and integration code.
    - Provides the Output Port adapters for the Repository interfaces that are defined in the
      `xxxx-application-service` submodule, and are injected there, into the application service implementation
      (IOC).
    - In this implementation, Entities are defined using JPA annotations.
    - Mapping is done between the JPA Entity and the Domain Entity Models using Mapstruct. We don't want to bleed
      the details of the JPA integration in to the Domain Core.
    - Integration is handled by Spring-Data via `JpaRepository`.
    - Database initialisation and migration is done using Liquibase.
  #### `xxxx-messaging`
    - Implements Kafka Message Listeners which delegate to the Listeners that are implemented in the
      `xxxx-application-service` submodule.
    - Implements Kafka Message Publishers, which implement the Publisher interface that is defined in the
      `xxxx-application-service` submodule, and are injected there, into the listener and application service
      implementations (IOC).

### `xxxx-container`

- Integrates all the above submodules together in a `@SpringBootApplication`.

## Submodules

The project is structured along the lines of the definitions given above:
![order-container.png](docs/images/order-container.png)

Note that `xxxx-domain-core` submodule at the bottom has **no** upward dependencies - the business logic and model are
completely agnostic to the messaging, database and REST API implementations that are integrated in the Spring
application.

Note also that the `xxxx-container` submodule at the top depends on **all** the other modules, apart from the
API spec. This is because we have disabled transient dependencies in the build to prevent libraries from being used in
our source code that are not approved.

Accordingly, the project submodule structure looks like this:

```
ticketing
├── airline-approval-service
│   ├── airline-approval-container
│   ├── airline-approval-domain
│   │   ├── airline-approval-application-service
│   │   └── airline-approval-domain-core
│   └── airline-approval-infrastructure
│       ├── airline-approval-data-access
│       └── airline-approval-messaging
├── airline-mdm-service
│   ├── airline-mdm-container
│   ├── airline-mdm-domain
│   │   ├── airline-mdm-application-service
│   │   └── airline-mdm-domain-core
│   ├── airline-mdm-infrastructure
│   │   ├── airline-mdm-data-access
│   │   └── airline-mdm-messaging
│   └── airline-mdm-presentation
│       ├── airline-mdm-api-spec
│       ├── airline-mdm-rest-controller
│       └── airline-mdm-swagger
├── common
│   ├── common-application
│   ├── common-domain
│   ├── common-helper
│   ├── common-infrastructure
│   ├── common-kafka
│   │   ├── kafka-libs
│   │   └── kafka-model
│   ├── common-presentation
│   └── common-saga
├── customer-mdm-service
│   ├── customer-mdm-container
│   ├── customer-mdm-domain
│   │   ├── customer-mdm-application-service
│   │   └── customer-mdm-domain-core
│   ├── customer-mdm-infrastructure
│   │   ├── customer-mdm-data-access
│   │   └── customer-mdm-messaging
│   └── customer-mdm-presentation
│       ├── customer-mdm-api-spec
│       ├── customer-mdm-rest-controller
│       └── customer-mdm-swagger
├── docs
│   └── images
├── infrastructure
│   ├── docker-compose
│   └── kubernetes
├── order-service
│   ├── order-container
│   ├── order-domain
│   │   ├── order-application-service
│   │   └── order-domain-core
│   ├─ order-infrastructure
│   │   ├── order-data-access
│   │   └── order-messaging
│   └── order-presentation
│       ├── order-api-spec
│       ├── order-rest-controller
│       └── order-swagger
├── payment-service
│   ├── payment-container
│   ├── payment-domain
│   │   ├── payment-application-service
│   │   └── payment-domain-core
│   └── payment-infrastructure
│       ├── payment-data-access
│       └── payment-messaging
└── quality
```

- The `docs` directory will contain our project documentation, in Markdown (.MD) format.
- The `infrastructure` directory contains configuration yaml files that are needed to run the backend services in Docker
  containers: Primarily postgres and Kafka.
- The `quality` directory contains gradle scripts and configuration files that integrate the static code analysis tools
  that we have integrated:  Checkstyle, PMD, SpotBugs and Gradle Lint.

## Packages

Where a submodule contains Java source code, we use a standardised package structure:

- All packages start with `com.acroteq.`
- Packages in a microservice start with `com.acroteq.xxxx.service.`, e.g. `com.acroteq.order.service.`
- Packages in a `xxxx-container` submodule start with `com.acroteq.xxxx.service.container`
- Packages in a `xxxx-data-access` submodule start with `com.acroteq.xxxx.service.data-access`
- Packages in a `xxxx-messaging` submodule start with `com.acroteq.xxxx.service.messaging`
- Packages in a `xxxx-domain` submodule start with `com.acroteq.xxxx.service.domain`
- Packages in a `xxxx-presentation` submodule start with `com.acroteq.xxxx.service.presentation`

This gives us the following packages in the `order-service` submodule:

```
com.acroteq.order.service.container.*
com.acroteq.order.service.data-access.*
com.acroteq.order.service.domain.*
com.acroteq.order.service.messaging.*
com.acroteq.order.service.presentation.*
```