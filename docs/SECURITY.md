# Security

## Authentication

- Authentication is provided by KeyCloud, which is configured to run in a docker container for test purposes.
  [docker-compose.yaml](../infrastructure/docker-compose.yaml).
- Swagger UI redirects to the KeyCloud Login page for authentication and stores the resulting access token (JWT) in its
  local Javascript session.
- The access token (JWT) is then included in the subsequent REST API Request headers.
- Spring Security is set up with OAuth2 to use KeyCloak as a provider and establish trust using the JWT access token.
- The JWT `preferred_username` is extracted from the token and stored in the Spring Security Context for use in Spring
  Data auditing attributes later on.
- This is pretty much the standard Spring-Boot Security setup:
  [JwtSecurityConfiguration.java](../common/common-container/src/main/java/com/acroteq/ticketing/container/config/JwtSecurityConfiguration.java)

## Authorisation

### Role-based access control

- User Roles can be defined in KeyCloud, using OAuth 'Scopes'.
- Spring Security can be configured to allow or deny access to REST end-points based on the user's roles (or Scopes).
- Not currently implemented in this project (yet) mainly because implementations can be very different, depending on
  project requirements.

### Row-Level-Security

- Requires access codes to be modelled in the persistence model, and database queries to filter the data using
  conditions in the query WHERE clause.
- Manual implementation of ACL query conditions in the `Repository` classes is expensive, error-prone, and needs
  rigorous testing.
- A generic 'Hibernate Filter' can end up being very complex and performance bottleneck.
- Neither approach is implemented here (yet).

## Propagation of Security Context

- The Orchestrator microservice that is sending a Saga Request Message must implement the required authorisation
  logic.
- If a message consumer is allowed to connect to a Kafka Topic, then it must be assumed that the publisher of that
  message has been authenticated and is authorised to perform this operation using the given input data.
- We do **not** propagate the user's credentials or a user ID token across domain boundaries to other microservices. It
  is a cardinal rule that an ID token should **never** be forwarded to some other entity besides the client that
  received it.
- If local Authentication and Authorisation are essential in a microservice, then we will have to propagate the user's
  JWT access-token in a custom Kafka message header (="token relay") and have the recipient microservice establish trust
  with the authorisation service (KeyCloud) directly.

## Kafka Security

- Security is provided by configuration of Kafka publishers and consumers to use TLS certificate encryption and
  authentication.
- See
  [Kafka: Encrypt and Authenticate with TLS](https://docs.confluent.io/platform/current/kafka/authentication_ssl.html).
- TLS authentication ensures that our Kafka EntityEvent Handlers and SagaRequest/Response Listeners can not be invoked
  by a malicious third party.
- TLS encryption ensures that the messages are safe from en-route listeners reading the packets.

### Spring Configuration

```yaml
  security:
    oauth2:
      client:
        registration:
          keycloak:
            client-id: ticketing
            authorization-grant-type: authorization_code
            scope: openid
        provider:
          keycloak:
            issuer-uri: http://localhost:8080/realms/acroteq
            user-name-attribute: preferred_username
      resourceserver:
        jwt:
          issuer-uri: http://localhost:8080/realms/acroteq
```