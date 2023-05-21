Testing

> All production code must be tested, both manually, in a running environment, and by automated tests. This includes
> both Backend and Frontend code, and both Business Logic and Infrastructure, irrespective of language. No exceptions.

> Any significant change to existing production code **must** break existing tests. If this is not the case, then we
> need more test coverage.

> Any bugfix must come with an automated test that reproduces the error and proves that the bug is indeed fixed. The
> test implementation should reference the Jira ticket number that reported the bug, for reference.

> A test without assertions is futile and a waste of time and money. This applies to any type of test, including
> performance tests.

## Unit Testing

- A Unit tests a single class, and mocks all of its dependencies.
- The intention is to specify the behaviour of the class and prove that it works as intended.
- It protects the class from later changes that would break the existing contract.
- Unit tests are implemented with [Spock](https://spockframework.org/) and [Groovy](https://groovy-lang.org/).
  (Where Spock causes issues (very rare), fall back to [JUnit 5](https://junit.org/junit5/docs/current/user-guide/)).
- Mock all class dependencies using Spock
  or [Mockito](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html) mocking.
- Use Spock's Data Tables to cover all the edge cases you can think of, including invalid input and error conditions.
- Assert all output, including error and exception messages.
- We do not follow the rule that each unit test may contain exactly 1 assertion. Use as many assertions as required to
  prove that the output is as expected.
- Spock assertions are done using simple Groovy expressions.
- JUnit assertions are implemented using [Hamcrest](https://hamcrest.org/JavaHamcrest/tutorial).
- Minimum code coverage of 95% is required, but remember that it's not about covering lines of code, it's about covering
  edge cases.
- We don't provide unit tests for:
    - Spring Configuration classes
    - Spring Properties classes
      (these should be covered by integration tests)
- Everything else is **fully covered** by Unit tests!

> See Spock unit tests implemented in the common submodule.

## Behavior-Driven Development

- Behavior-Driven Development specifies and tests the business logic in the Domain Core using a common domain language.
- Implemented with [Cucumber](https://cucumber.io/) in [Gherkin](https://cucumber.io/docs/gherkin/).
- PO, BA and work Devs work together to define the common domain language (given ... when ... then ...).
- PO and BA write the Feature files. These are living documents which define the spec and drive the tests.
- In contrast to unit tests, Cucumber tests run against the full Domain-Core Layer:  all business model and service
  classes running together as an ecosystem.
- Multiple tests per use-case
    - as many edge-cases as possible
    - expected error conditions
- Implement assertions on all output that is salient for the use-case and edge-case.
- Step definitions are implemented on-demand by Java Devs.
- Scenarios and Features are implemented by BAs and Devs working together (!)

> See the Cucumber tests in the payment-domain-core submodule.

E.g. This is a working unit test:

```gherkin
Feature: Payment Validation
  The payment should only be allowed to complete if the Customer has enough credit

  Scenario: New customer has enough credit for the requested payment
    Given a customer with a credit limit of CHF 2000.00
    And a credit balance of CHF 2000.00
    And a credit limit update credit change of CHF 2000.00
    And a pending payment value of CHF 250.00
    When the payment is validated
    Then the payment is completed successfully
    And the payment validation result is approved
    And the updated credit balance is CHF 1750.00
    And the new credit change is payment of CHF -250.0
```

## Integration Testing

- Tests a single running executable
- Proves that the ecosystem of classes works together as intended
- Few (minimum 1) tests per use-case, that must cover
    - the most common expected input
    - any frequent edge-cases
    - any *expected* error conditions
- Assert all output that is salient for the use-case
- Implemented with:
    - [Spring Test](https://docs.spring.io/spring-boot/docs/1.5.2.RELEASE/reference/html/boot-features-testing.html)
    - [JUnit 5](https://junit.org/junit5/docs/current/user-guide/)
    - [Mockito](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)
    - [Hamcrest](https://hamcrest.org/JavaHamcrest/)
    - [WireMock](https://wiremock.org/docs/)
    - [Test Containers](https://www.testcontainers.org/)
- Mock all external system dependencies, including REST client, messaging and database

## E2E Testing

- Tests the fully deployed and running set of microservices and infrastructure, including messaging and DB
- E2E tests are fully automated, run from Gradle as JUnit tests, against microservices and infrastructure that are
  started using Test Containers.
- Uses a curated database and a set of instrumented data constellations
- Smoke-tests only, no full coverage of use-cases or edge-cases
- Best case: includes the UI, driven by an automated testing tool like [Selenium](https://www.selenium.dev/)

## Performance Testing

- Tests the fully deployed and running set of microservices and infrastructure.
- Fully scripted and automated, but run manually against a dedicated environment (and dataset) that is as close to
  production as possible.
- Performance testing datasets should either be anonymized from an existing production system, or generated  
  synthetically to match the volume and variability of the expected production data as closely as possible. Both of
  these alternatives take time and effort!
- Performance testing scripts should follow the most common use-cases of the application, and should cover all the major
  functional areas.
- Scripts should be developed and performance tests should be run from the outset of the project, in parallel with
  production and other testing code, to avoid a deficit and technical debt late in the project.

> Just before production deadline is too late to start with performance tests!  Even if we manage to get the dataset
> generated, and the scripts implemented and run in time, we are likely to run in to technical issues that will be
> difficult and expensive to solve. Running performance tests from the start allows us to catch issues before they
> become monsters.

### 5 types of Performance Test:

#### Load Testing

- "Flight Load" = maximum expected load under normal conditions.
    - Defined by the PO/client.
    - E.g. Concurrent users and hits per second.
- "Design Limit Load" = "Flight Load" multiplied by a "design factor" (normally 1.5).
- Slowly ramp load up to "Design Limit Load", and maintain for 1 or 2, or until response times are stable.
- Define SLAs: maximum response time requirements at "Design Limit Load".
- Measure and report/graph response times over time, including std-dev and outliers
- Measure and report/graph resource usage over time: e.g. CPU, Memory, Swap, Heap, Garbage Collection, Network
  IOPS/throughput, Storage IOPS/throughput, etc.
- Show that the microservice eco-system ramps up as expected as load is increased, and spreads the load evenly.
- Show that there are no 'hot partitions' in Kafka.
- Use assertions to prove that the application is working as designed under maximum expected load
    - Correct, expected output
    - No session leaks or other concurrency effects

#### Spike Testing

- "Ultimate load" = "Design limit load" multiplied by a "Safety factor" (normally 1.5)
- Slowly ramp load up to "Design Limit Load", and maintain until response times are stable for at least 10 minutes.
- Quickly ramp load up to "Ultimate Load" and maintain for a short period of time (~5-10 minutes)
- Use assertions to prove that correct functionality is not impacted. SLA-Response times may be exceeded, but *no
  timeouts*.
- Measure and report/graph response times at "Ultimate Load" for documentation.
- Allow load to ramp back down to "Design Limit Load".
- Main goal is to show that there are no errors at "Ultimate Load", and response times recover back to levels defined by
  SLAs when the load is reduced back to "Design Limit Load".

#### Stress Testing

- Load is slowly ramped up well above the "Ultimate Load", until the system fails in some way.
- Intention is to see what the system can handle, as well as when and where will it start to fail.
- Questions to answer:
    - Did we lose data?
    - Was something corrupted?
    - What was the user experience of the failure?
- Can we improve our implementation to prevent destructive failure modes?

#### Soak Testing

- Long-term load tests at "Design Limit Load"
- Intention is to try to provoke long-term failure modes, such as memory leaks, insufficient storage, throughput
  limits,  
  etc., and also to detect rare bugs, such as race conditions or session leaks.
- Duration can be anything from a couple of hours to a couple of days, depending on requirements.

#### Availability and Resilience Testing

- What happens when a pod dies or is killed under load?
- SLA should define the maximum acceptable impact on the user experience.  E.g.  Maximum downtime, session loss, etc.
- Run a standard "Load" test and when the response times have stabilised, kill a pod.
- The system should recognise the failure and react by
  - redistributing the load to the remaining pods.
  - starting up a new pod to replace the one that failed.
- Run this test for each microservice, as well as for each infrastructure container.
- It should not be possible to force a catastrophic system shutdown.
