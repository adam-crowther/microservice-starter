# Implementation

- ValidationResult needs to be translatable, include i18n keys.
- Exception Types: DomainException, ApplicationException, PresentationException, InfrastructureException
- Eventual Consistency
- Dockerfile for the containers/services

# Testing

- Spock unit tests
- Cucumber tests
- Integration tests

# Spring

- Clean shutdown in Intellij
- Try gradle dependency manager plugin

# Database

- Logical delete / Historized data

# Security

- Propagate JWT token from sender to recipient via Kafka messages
- Entity level security for Authorisation
- Security/Vulnerability dependency audit
- Override authorisationUrl in openapi.yaml

# Logging

- Configure all logging so that it includes the sagaId from the Session. CorrelationId must be enriched in all
  messages using an interceptor, and stored in the Session.



 