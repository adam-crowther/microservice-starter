# Testing

## Unit Testing

- Tests a single class.
- Implemented with [Spock](https://spockframework.org/) and [Groovy](https://groovy-lang.org/).
- Where Spock introduces unnecessary issues, fall back to [JUnit 5](https://junit.org/junit5/docs/current/user-guide/).
- Mock all class dependencies using Spock
  or [Mockito](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html) mocking.
- Cover all the edge cases you can think of, including invalid input and error conditions.
- Assert all output, including error and exception messages.
- Assertions are done using Spock or [Hamcrest](https://hamcrest.org/JavaHamcrest/tutorial).

## Business Unit Testing

- Tests the business logic in the Domain Core
- Multiple tests per use-case
    - as many edge-cases as possible
    - expected error conditions
- Assert all output that is salient for the use-case and edge-case
- BDD: Implemented with [Cucumber](https://cucumber.io/) in [Gherkin](https://cucumber.io/docs/gherkin/)
- Step definitions are implemented on-demand by Java Devs.
- Feature files, containing the Scenarios, are implemented by Business Analysts (!)

## Integration Testing

- Tests a single running executable
- Proves that the full ecosystem of classes works together as intended
- Few (minimum 1) tests per use-case
    - the most common expected input
    - expected error conditions
- Assert all output that is salient for the use-case
- Implemented with [JUnit 5](https://junit.org/junit5/docs/current/user-guide/)
- Mock all external system dependencies, including REST client, messaging and database

## E2E Testing

- Tests the fully deployed and running set of microservices and infrastructure, including messaging and DB
- Uses a curated database and a set of instrumented data constellations
- Smoke-tests only, no full coverage of use-cases
- Best case: includes the UI, driven by an automated testing tool like [Selenium](https://www.selenium.dev/)

## Performance Testing

- TODO

