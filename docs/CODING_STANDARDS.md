# Coding Standards

> The primary consumer of your code is **not** the compiler, but the next developer, or you in 6 months. Make it
> readable and easy to understand first, and make it work second.

[KISS](https://medium.com/sliitwif/keep-it-simple-stupid-the-kiss-principle-guide-to-developers-d6ad83145955),
[YAGNI](https://martinfowler.com/bliki/Yagni.html),
[DRY](https://medium.com/code-thoughts/dont-repeat-yourself-caa413910753),
[SOLID](https://www.baeldung.com/solid-principles)

## Code style

- Our code style is loosely based on the Google style, with some modifications to make it more readable, more lenient
  and easier to conform.
- The Intellij IDEA formatter will do 99% of the code formatting.
- Blank lines in code can organise it in to logical blocks, and make it easier to read.
- The page width is set to 120 columns, and Checkstyle is set to enforce 150 columns to allow for overflow in some cases
  where it is needed for readability.
- Be neat. Follow the rules of 'Clean Code'.

## Avoid boilerplate by using declarative design

- Use [Lombok](https://projectlombok.org/) and [Mapstruct](https://mapstruct.org/documentation/stable/reference/html/)
  as much as possible to avoid boilerplate.
- We allow the use of Lombok's experimental
  [Superbuilder](https://projectlombok.org/features/experimental/SuperBuilder), because it is very mature and has been
  around for a very long time. Without it, builder pattern in inheritance hierarchies gets painful. Other experimental
  features should **not** be used.
- **Don't** introduce complexity to allow the use of Lombok or Mapstruct. If the code is simpler without, then do it
  without.

## Interfaces with clear Intent

- Choose names of interfaces and methods such that the semantics are clear and obvious to the reader.
- The next developer should not have to go and read the implementation to understand the intent.
- Use TDD: writing the test first forces us to think about the semantics of the interface.

## Naming Conventions

- Naming is one of the most important tasks in software engineering, and one of the most difficult to get right.
- Names must match the 'Ubiquitous Language' of the Domain, which is shared by the Domain Experts (Business
  Analyst, Product Owner, Client).
- Names must be meaningful, clear and unambiguous. They must not be misleading.
- Class names use `CamelCase` and start with an upper case letter.
- Local variables, class attributes, and method parameters use `camelCase` and start with a lower case letter.
- Constants (static final) use `SCREAMING_SNAKE_CASE`.
- Method names use `camelCase` and start with a lower case letter.
- No abbreviation, except where it is a standard acronym in the domain (e.g. 'forex') or the technology (e.g. 'JPA')
  namespace.
- Use verbose names as much as possible, but don't repeat redundant elements of a name if these clear from the
  context.<br>Eg: `final CreateOrderCommandDtoToDomainMapper orderMapper;` is enough if there are no other mappers in
  the context of the class or method.
- Method names usually start with a verb.
- Variable names are usually nouns.

## Methods

- A method should do only one thing
- It should stick to a single level of abstraction below the name of the method.
- Keep methods small - you will need a very good argument to allow more than 10 lines.
- No method should need more than 3 arguments. More is a code smell that indicates that refactoring is needed.
- Methods should be organised to be read like a top-down narrative.
- Command Query Separation: Methods should either do something or answer something, but not both.
- Every method has one entry point and one return at the end. We do not allow a return in an if statement, halfway down
  the method.

- No Side-Effects:

    - Methods **must not** change the internal state of their input parameters.
    - Place complex logic inside Domain Entities and Value Objects, and have them change their state from within.
    - Separate commands from queries - a method that changes the internal state of an object should not return domain
      information.

## Classes

- Keep classes small and cohesive.
- Each class should have a single responsibility.
- No public or package visibility attributes.

## Constructors vs Builder Pattern

- Builder pattern is always easier to read than a constructor, and with Lombok, it is not even more verbose.
- Where the constructor only has a single parameter, we prefer a simple static factory method (e.g.
  `final ValidationResult result = ValidationResult.of("Something went wrong");`).
- When building very complex objects, [Fluent Interface](https://martinfowler.com/bliki/FluentInterface.html) pattern
  can help.

## Lambdas and Method references

- Lambdas can get unreadable very quickly, and therefore need to be kept simple. E.g. this is ok:<br>
  `.map(totalMassKg -> totalMassKg + luggageMassKg)`
- Lambda variables must be named according to the naming conventions.  `a` is not ok.  `aircraft` is ok.
- Use method references where possible:
  ```java
  final int passengersOnBoardCount = passengers.stream()
                                               .filter(Passenger::hasTicket)
                                               .map(aircraft::boardPassenger)
                                               .count();
  ```
- We don't allow Lambda blocks. Refactor these to a private method, and reference the method. Probably the method
  actually belongs in another class, e.g. the entity.
- Java does not support currying properly, but we can get close and avoid nesting methods in Lambdas by providing
  methods that return Function<>, Predicate<> or Consumer<>:
  ```java
  aircraft.stream()
         .filter(hasConflicts(traffic))   // much easier to read than a lambda
         .forEach(calculateDeconflictionVector(traffic, weather));  // another example - this time a BiConsumer<>.
  ...
  
  /* Simple 'Currying' method, wraps the overridden implementation method below. */
  private Predicate<Aircraft> hasConflicts(final Traffic traffic) {
    return aircract -> hasConflicts(aircraft, traffic)
  }

  /* Implementation method. */
  private boolean hasConflicts(final Aircraft aircraft, final Traffic traffic) {
    return <calculation logic here>;
  }
  ``` 

## Boxing and Unboxing

- Prefer primitive types to wrapped objects. Only use the wrapped object if the semantics of the attribute/variable
  allow it to be null.
- In general, avoid unnecessary boxing and unboxing, and maintain the wrapped or primitive type if you can.
- If that is not possible, then make sure you have the `null` case under control.

## Nesting

- Avoid method and operator nesting - it makes the code very hard to read.
- At most, we accept 1 level of nesting and a single argument, **if** the nested method is a simple getter. e.g. <br>
  `final BigDecimal airspeed = aircraft.calculateTrueAirspeed(environment.getDensityAltitude());`<br>
  Even this is easier to read if we extract a local variable for densityAltitude:<br>
  ```java
  final BigDecimal densityAltitude = environment.getDensityAltitude();
  final BigDecimal airspeed = aircraft.calculateTrueAirspeed(densityAltitude);
  ```
  There is no performance impact to doing this, and it makes the code much easier to read.
- Do **not** nest the following:
    - Lambdas
    - Logical expressions
    - Unary or ternary (conditional) operators. Don't do this:  `if (value++) {` or this: `if (++value) {`.
    - Loops
    - If or switch blocks
    - Try/catch/finally blocks

## Ternary (Conditional) Operator

- The ternary operator may only be used for extremely simple expressions. E.g. this is ok:<br>
  `final AircraftState state = hasWeightOnWheels ? ON_THE_GROUND : IN_THE_AIR;`<br>
  Any more complex than that needs to be refactored to an `if` statement, and probably moved to a method in the Aircraft
  class.

## Immutable Entities and Objects

- Immutable objects are mandatory in the DTO, Domain Entity and JPA Entity models. All attributes must be final, all
  collections must use
  the [Guava Immutable Collections](https://github.com/google/guava/wiki/ImmutableCollectionsExplained) library.
- Elsewhere, use immutable objects wherever possible.
- Use the final keyword wherever possible, on local variables, method parameters, object attributes, etc.
- If we need to change an object's state, then we generate a new object with the new state and use that one moving
  forward.
- Using immutable entities has a couple of benefits:
    - It makes side effects impossible: there is nothing worse than passing an object in to a method as a parameter, and
      having its state changed unexpectedly and invisibly.
    - It reduces the opportunity for concurrency issues. An object's internal state can not be changed unexpectedly by
      another thread.
    - Security: it's not possible to change the state of an object's internal attributes using a reference that was
      either passed in through the constructor or a setter, or out through a getter.

## Nullability and Optional

> A NullPointerException in production is **always** a developer error.

- Wherever possible avoid the issue completely by giving variables semantics with a well-defined default value, matching
  the normal 'happy' flow.
- **Never** assign a variable to null deliberately, and **never** deliberately return null from a method.
- Use Java's `Optional` class often, especially with the `.map()` method. It forces us to think about the null case
  actively.
- Don't use `Optional.get()`. Think about a better 'default' semantic, or use `.orElseThrow()` to throw a more
  meaningful exception.
- It is our policy to use annotations to validate all input and catch null pointer exceptions as early as possible:
    - Use the Lombok [@NonNull](https://projectlombok.org/features/NonNull) annotation:
        - in Domain Entities and ValueObjects
        - in external API methods
    - Where an external API method parameter or a Domain model attribute is nullable by design, we use the SpotBugs
      [@Nullable](https://www.javadoc.io/doc/com.github.spotbugs/spotbugs-annotations/3.1.0/edu/umd/cs/findbugs/annotations/Nullable.html)
      annotation to make this explicit.
    - In JPA Entities, use
      [@Column(nullable = false)](https://docs.oracle.com/javaee/6/api/javax/persistence/Column.html#nullable())
      as much as possible, to force the database to generate a constraint.
    - In DTOs in the application service interface, use the Jakarta [@NotNull]() annotation instead of Lombok, so that
      we
      make use of the
      [Spring Bean Validation](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#validation)
      framework.

## Minimise visibility

- Set up Intellij to highlight classes, methods and attributes that could have less visibility, and use the lowest
  visibility possible.
- **Never** give an object's attribute `public` visibility.
- Prefer 'package' visibility over `protected`, which is nearly never needed.
- `public static final` constants can be public if they are used elsewhere.
- We will soon set up the SonarQube static code analysis, which will fail the build if a classes, methods or attribute
  could have lower visibility.

## Method chaining

- Law of Demeter: a module should not know about the inner implementation of the objects that it manipulates.
- This called a train wreck, and is **wrong**:<br>
  `Long heading = flightManager.getInitialReferenceSystem().getHeading();`<br>
  If you need to get the heading from the flightManager, then it should be exposed as a method.
- Train wrecks are generally considered sloppy style. There's no performance disadvantage to splitting them up.
- Exceptions: Builder and Fluent Interface pattern.

## Don't ust Inheritance in procedural code

- Inheritance models an "is-a" relationship.
- **Don't** share functionality between Services and Components by having them inherit a common abstract base class.
- Instead, create another Service or Component class that performs the common functionality, and include it by
  composition.

## Magic literals

- Do **not** put magic numbers, strings or other values in code. They hide your intent and make it hard for the reader
  to understand what you are trying to say.
- This is wrong:
  ```java
  if (totalMass > 412770) {   // what is 412770?
     throw ...
  }
  ```
  This is clear:
  ```java
  private static final int MAXIMUM_TAKEOFF_MASS_KG = 412770;
  ...
  
  if (totalMass > MAXIMUM_TAKEOFF_MASS_KG) {
     throw ...
  }
  ```
  Note that we always include the unit in the name of the constant.
- Consider if the constant value should actually be configurable.

## Comments

- Comments in code are a code smell, telling us that we need to restructure and reconsider our naming.
- They get out of date and out of sync with the code, and that is when they mislead the reader.
- They bloat and clutter the code.
- Javadoc: Only on external, public facing APIs.
- TODO and FIXME comments can be used to indicate technical debt, but must reference a Jira ticket number that addresses
  the debt.

## Local type inference (var)

- Use of the var keyword is optional.
- You may use it where it makes sense, and where it makes the code more readable.
- The type of the variable **must** be clear from the context, otherwise use the explicit type.

## Concurrency / Parallel Computing / Multi-threading

> Golden rule: Don't use concurrency. And if it's absolutely necessary: don't use concurrency.

- In general, don't use concurrency. Writing correct parallel code is hard for anyone.
- Thread synchronisation is
- Concurrency errors are hard to reproduce, hard to debug, and hard to fix:
    - Deadlocks
    - Race Conditions (= Thread interference)
    - Data consistency
    - Starvation
- If you think you need concurrency, talk to the project architects.
    - The design and implementation will need to be approved.
    - The implementation will need to be covered by integration tests to prove that it does not cause issues.

## Libraries

- We accept the following very basic and common library dependencies in our Domain Core submodules:
    - [Lombok](https://projectlombok.org/)
    - [Apache Commons Lang](https://commons.apache.org/proper/commons-lang/javadocs/api-release/index.html)
    - [Google Guava](https://github.com/google/guava#readme)
    - [Slf4j](https://www.slf4j.org/docs.html)

  These are used to help with cleaner code and avoid boilerplate as much as possible.
- We also make use of the following common libraries outside our Domain Core:
    - [Mapstruct](https://mapstruct.org/documentation/reference-guide/): for declarative, annotation-based mapping of
      similar datastructures between layers.
    - ... TODO

## Events / Messages

- Asynchronous messages are defined using Avro, and messages are validated at runtime
  using [Schema Registry](https://docs.confluent.io/platform/current/schema-registry/index.html).
- Avro model: change management and backward / forward compatibility
    - Use optional fields as much as possible, by providing defaults.

- Entities are mirrored from one Microservice persistent store (e.g. database) to another using CRUD-Events and the
  principles of CQRS.
- **Avoid request/response cascades at all costs.**
    - A microservice must be able to respond to a Query directly, without triggering more requests to other
      microservices.
    - We achieve this using Event driven architecture - microservices listen to Event streams and persist any state
      changes to the core entities that they will require.
    - Each microservice gathers and maintains a store of all the data that it will need to provide the required service.

## Exception Handling

- Do **not** return error codes, or (even worse) special error values.
- Only use checked exceptions when you require the caller to catch and handle the thrown exception directly.
- Do not throw the generic Java exception types. If you need to throw an exception, create a new one that derives (
  directly or indirectly) from `DomainException`, and configure it with the required context information.
- Exceptions are reported to the caller as standard `Problem` structures.
- The `Problem` structure must include:
    - a descriptive message in the user's session language, or in English where this is not available.
    - a unique `I18N` key.
    - String parameters that are needed to translate the `I18N` key
- Exceptions are reported back through the REST API using a `GlobalExceptionHandler` class, which is annotated with the
  Spring `@ControllerAdvice` annotation.
- Exceptions while processing an asynchronous API are handled by generating a corresponding failure response,
  e.g. `PaymentResponseMessage` with `paymentStatus = FALIED`.

## Logging

- Use `Slf4j` API.
- Be systematic in your logging. Generate a debug-level log on input and output, at every external interface, and at
  every layer:
    - Domain service
    - Application service
    - Message listener
    - Message publisher
    - REST API controller
- Add trace-level logs where appropriate in complex code or for debugging, and do not remove them when you are done.
  They can be useful in production and activated if needed.
- Do **not** generate an error or warning log and then throw a new exception. If there is new context information, then
  create and throw a new exception. The exception should contain all the required data, and it should be logged when it
  is caught. Logging before throwing the exception just causes the same error to be logged twice.
- TODO: All logging must include the correlationId from the context. This will be enriched automatically.


