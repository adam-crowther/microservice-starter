# Database design

- Each microservice has its own independent data repository.
- These do not need to be relational databases, but where they are, we allow each microservice to use a different schema
  in the same database instance.
- We do **not** allow cross-schema queries or views. A Microservice **must not** access another microservice's data
  repository directly.
- We use JPA annotations in JPA Entity classes to define the persistence schema.
- This project uses PostgreSQL, running in a Docker Container.

# Master and Replicated models

- JPA Entity
  Diagram: [![jpa-entity-diagram-thumb.png](images%2Fjpa-entity-diagram-thumb.png)](![jpa-entity-diagram.png](images%2Fjpa-entity-diagram.png))
- There must be exactly one source of truth for each entity: one microservice that is allowed to change the entity's
  data (=Command). All others are only allowed read access (=Query).
- The microservice that is allowed to modify the entity is called the 'Master' of that data.
- Most microservices will have 2 kinds of entity - those which they control as 'Master' (="Master Data") and some that
  they can only query, because another microservice is the master (="Replicated Data").
- Since a Microservice must not access another microservice's data repository directly, data is replicated across to
  other microservices using CRUD events: Create, Update and Delete.

## Master Data

- Master Data is "owned" directly by the microservice in question.
- It is fully DDD-modelled into entities and aggregates.
- Aggregates are kept internally consistent by the domain validations and logic.
- All database updates and most database queries are performed on the aggregate root.
- Master JPA Entities inherit from class `MasterJpaEntity`.
- The `MasterJpaEntity` parent class includes 'Auditing' attributes, which is enriched by Spring-Data.

  | Type    | Field                 | Annotation          | Description                                        |
      |---------|-----------------------|---------------------|----------------------------------------------------|
  | String  | createdBy             | `@CreatedBy`        | The principal (user) that created the entity       |
  | Instant | createdTimestamp      | `@CreatedDate`      | The date that the entity was created               |
  | String  | lastModifiedBy        | `@LastModifiedBy`   | The principal (user) that last modified the entity |
  | Instant | lastModifiedTimestamp | `@LastModifiedDate` | The date that the entity was last modified         |

## Replicated Data

- Replicated Data is "owned" by another microservice and "replicated" in to this one using CQRS events.
- Replicated Entities
    - do not need to persist the full data model that is provided by the events
    - must **only** be created and updated by their event handlers.
    - can be referenced by the microservice business model and business logic, but this is strictly limited to read-only
      access (= "Queries")
- Replicated JPA Entities inherit from class `ReplicatedJpaEntity`.
- The `ReplicatedJpaEntity` includes the `EventId` attributes, which are taken from the Kafka event: partition and
  offset.
- The `EventId` is used to recognise event ordering errors and implement idempotent event processing.

# Optimistic Locking

- All tables are protected from dirty writes
  by [Spring Data optimistic locking](https://docs.spring.io/spring-data/jdbc/docs/current/reference/html/#jdbc.entity-persistence.optimistic-locking)
- To achieve this, the JPA entities include a Long attribute called version, which is annotated with `@Version`.
- On update, Spring checks that the version stored in the database is actually unchanged from that in the entity,
  using a where clause in the update statement.
- If another thread has updated the entity since it was loaded, then the version will not be the same in the database
  record, and an OptimisticLockingFailureException will be thrown.

## Liquibase

- Database schema migration is handled by
  [Liquibase](https://docs.liquibase.com/concepts/introduction-to-liquibase.html).
- Our migrations are kept as database-agnostic as possible by using the Liquibase YAML scripts.
- Changes to the schema can be prototyped using the diffChangeLog gradle task.
- Our Gradle setup starts up a clean PostgreSQL instance, applies the existing Changelog from the `xxxx-data-access`
  resources directory, and then runs the diffChangeLog task against that fresh schema. This gives the developer a
  starting point for the migration script, but it's **not** enough to just run the diff and commit the generated script.

### Liquibase coding conventions

Follow the best practices defined here:

Specifically:

- Each ChangeSet must contain only one Change. This is required in order to allow migration scripts to be rerunnable.
- The changeSet `author` must be set to your name and user-name.
- The changeSet `id` must be set to a human-readable description, including the Jira Ticket number that covers your
  work.

These last 2 rules are required because Liquibase outputs the changeSet id and author in production logs if something
goes wrong and the migration fails. Without readable entries it's very hard to troubleshoot.

```yaml
- changeSet:
  id: TKT-1234_AddCreditLimitToCustomerEntity_createTableCustomers
  author: Adam Crowther (adamcc)
  changes:
    - createTable:
      columns:
        - column:
          ...
```
