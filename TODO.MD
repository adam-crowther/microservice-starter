# Implementation

- ValidationResult needs to be translatable, include i18n keys.
- Exception Types: DomainException, ApplicationException, PresentationException, InfrastructureException
- Eventual Consistency
- Dockerfile for the containers/services

# Spring

- Upgrade 10 Spring Boot 3.1.0

# Security

- Role-based Authorisation of REST APIs
- Row-level-security
- Security/Vulnerability dependency audit
- Override authorisationUrl in openapi.yaml

# Logging

- Configure all logging so that it includes the sagaId from the Session. CorrelationId must be enriched in all
  messages using an interceptor, and stored in the Session.
 