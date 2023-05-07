# Implementation

- ValidationResult needs to be translatable, include i18n keys.
- Exception Types: DomainException, ApplicationException, PresentationException, InfrastructureException
- Eventual Consistency
- Dockerfile for the containers/services

# Testing

- Spock unit tests
- Integration tests

# Spring

- Clean shutdown in Intellij

# Database

- Logical delete / Historized data
- Relationship mapping in Entity model

# Security

- Spring Security: Authentication and Authorisation
- Entity level security for Authorisation
- Security/Vulnerability dependency audit

# Logging

- Configure all logging so that it includes the correlationId from the Session. CorrelationId must be enriched in all
  messages using an interceptor, and stored in the Session.



 