# Database design

- Each microservice has its own independent data repository.
- These do not need to be relational databases, but where they are, we allow each microservice to use a different schema
  in the same database instance.
- We do **not** allow cross-schema queries or views.
- We use JPA annotations in JPA Entity classes to define the persistence schema.
- This project uses PostgreSQL, running in a Docker Container.

## Liquibase

- Database schema migration is handled by
  [Liquibase](https://docs.liquibase.com/concepts/introduction-to-liquibase.html).
- Our migrations are kept as database-agnostic as possible by using the Liquibase YAML scripts.
- Changes to the schema can be prototyped using the diffChangeLog gradle task.
- Our Gradle setup starts up a clean PostgreSQL instance, applies the existing Changelog from the `xxxx-data-access`
  resources directory, and then runs the diffChangeLog task against that fresh schema. This gives the developer a
  starting point for the migration script, but it's **not** enough to just run the diff and commit the generated script.
