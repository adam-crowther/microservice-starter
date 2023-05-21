# Messaging

## Design / Project Structure

- Interfaces are defined as 'Ports' (Hexagonal Architecture) in the Domain layer's `xxxx-application-service` submodule.
- Ports are implemented by 'Adapters', which are located in `xxxx-message submodule`, in the `xxxx-infrastructure`
  submodule.
- Adapter implementations are injected in to the Domain/Application-Service layer at runtime by Spring IOC, which
  allows the domain logic to remain free of technical implementation details.
- In this project, asynchronous messaging is implemented using Kafka.

### Design Pattern: Saga

- A chain of local transactions that updates each service in sequence, and publishes a message or event to trigger the
  next transaction step.
- Saga flows are usually defined as a series of Request/Response message interactions, with an 'Orchestrator'   
  microservice controlling the flow and making the requests, and other microservices executing their respective business
  logic and sending response messages, which are received and processed by the Orchestrator.
- See
  [Medium: Saga Pattern for Microservices Distributed Transactions](https://medium.com/design-microservices-architecture-with-patterns/saga-pattern-for-microservices-distributed-transactions-7e95d0613345)

#### Saga implementation

- Saga Request and Response MessageListeners are implemented using the `KafkaSagaMessageHandler` class, which allows the
  application to map a different Consumer to each Avro MessageType that will be encountered in the topic.
- Saga Steps implement the
  [`SagaStep`](../common/common-saga/src/main/java/com/acroteq/ticketing/saga/SagaStep.java)
  interface, which defines 2 methods:
    - `process()` calls the Domain Services to process the business logic required by the step, and if necessary it uses
      the Port interfaces to persist data.
    - `rollback()`.
- Saga Steps return `Event` objects, each of which represents a state change, and can lead to a new message being
  published.
- E.g.:
    - `AirlineApprovalResponseKafkaListener` (Input Adapter in Infrastructure/Messaging Layer) listens on the
    - `airline-approval-response` topic and delegates to `AirlineApprovalResponseMessageListener` (Input Port in
      Domain/Application Service Layer).
    - The `airline-approval-response` topic can deliver 2 message types:
        - `AirlineApprovalApprovedResponseMessage`
            - &rarr; `AirlineApprovalResponseKafkaListener` delegates
              to `AirlineApprovalResponseMessageListener.orderApproved()`.
            - &rarr; which calls `OrderApprovalSagaStep.process()`.
            - &rarr; which executes the required business logic in `OrderDomainService` (Domain Core Layer) and saves
              the state.
            - No new message is published, because the Saga is complete.
        - `AirlineApprovalRejectedResponseMessage`
            - &rarr; delegated to `AirlineApprovalResponseMessageListener.orderRejected()`
            - &rarr; which calls `OrderApprovalSagaStep.rollback()`.
            - &rarr; which executes the required business logic in `OrderDomainService` (Domain Core Layer), saves the
              state, and returns an `OrderCancelledEvent`.
            - &rarr; `AirlineApprovalResponseMessageListener.orderRejected()` calls
              `PaymentCancelRequestMessagePublisher.publish()` (Output Port in Domain/Application Service layer), which
              is implemented by `PaymentCancelRequestKafkaMessagePublisher` (Output Adapter in Infrastructure/Messaging
              Layer).
            - &rarr;`PaymentCancelRequestKafkaMessagePublisher` publishes a `PaymentCancelRequestMessage` to the
              topic `payment-request`.
- In this scenario, the Order Service takes the role of the Saga Orchestrator, which controls the flow of messages and
  manages the state in a centralised fashion.

### Design Pattern: CQRS

- Each Master-Data Aggregate Root (e.g. `Customer`, `Airline`) has a 'Master-Data Management' (MDM) Microservice, which
  has a single responsibility: managing the persistent data in the Entities in that Aggregate.
- The MDM microservice is defined as the 'Primary' microservice for that data. It exposes "Commands" on these entities
  in the sense of CQRS.
- Other microservices that use these Entities are defined as 'Replicas' of this data, and are strictly **not** allowed
  to expose or implement "Commands" that modify these Entities.
- Entity state is propagated from the 'Primary' Microservices to the 'Replicas' via CQRS `EntityEvent` Messages.
- Each `EntityEvent` type is published to its own topic, which is consumed by the microservices that need that data.
- Eg the [airline_event.avsc](../common/common-kafka/kafka-model/src/main/avro/airline_event.avsc) is
  published by the `airline-mdm-service`, which is the 'Primary' for the `Airline` Aggregate, and consumed by
  the `order-service` and the `airline-approval-service` which need the `Airline` data to perform their use-cases.
- The `Replica` microservices might not need all the attributes that are managed and provided by the 'Primary'
  microservice, and so they will often have different, reduced data models for this data.
- Different microservices might even use different technology to store their data, depending on their requirement
  (RDBMS, No-SQL, In-Memory Cache, ...)

#### CQRS implementations

- CQRS events are modelled using Kafka KTable / KSQL messages.
- EventMessages are modelled in Avro.
- There is one EventMessage type per Aggregate Root, containing all the data for that Aggregate.
- The message key is the unique ID of the Aggregate Root Entity.
- Insert and Update operations are achieved by sending the same message with the same key and value structure.
- Delete operations are achieved by sending a message with the given entity's ID as the key, but a NULL value.  (Kafka
  calls this the 'Tombstone' message.)
- Following these conventions allows us to implement stream transformations using Kafka Streams or KSQL.
- CQRS EventMessages are implemented using the `KafkaEntityEventMessageHandler` class, which allows the
  application to defined a `createOrUpdateConsumer` and a `deleteConsumer` for the incoming messages.
  - `KafkaEntityEventMessageHandler` does not support multiple message types per topic, according to the convention.

## Error handling

### Publishing messages

- Transactional context includes message publishers, so that an Exception in business logic should roll back the
  database transaction and prevent the response message from being sent.
- An Exception sending the message triggers the retry mechanism in the Kafka Producer, which is configured according
  to [Kafka Producer Retries](https://www.conduktor.io/kafka/kafka-producer-retries/). If a broker goes down while
  sending a message, the client will negotiate to get another active replica broker from the cluster and retry.
- If the error is not retryable (e.g. configuration error) or if the retry fails too many times, then the database
  transaction will be rolled back, an error will be logged and the asynchronous recovery callback method will be
  invoked.
- The asynchronous recovery callback method should reverse state changes, trigger a semantic Saga rollback and/or
  generate an alert as required.
- All these scenarios need to be fully tested in integration and/or E2E tests, to prove that the state remains
  consistent, and no state gets lost.

### Consuming messages

#### Dead Letter Topic ('DLT') with Exponential Backoff

- Spring's `MessageListenerContainers` are configured with the `DefaultErrorHandler`, which is set up with
  a `DeadLetterPublishingRecoverer` and `ExponentialBackOffWithMaxRetries`.
- See
  [KafkaErrorHandlerConfiguration.java](../common/common-kafka/kafka-libs/src/main/java/com/acroteq/ticketing/kafka/consumer/config/KafkaErrorHandlerConfiguration.java)
- The behaviour is configurable - e.g. max 3 retries, with a delay that starts at 10 seconds, and doubles with each 
  attempt.
- Any exception in one of the `KafkaMessageHandlers` (which delegate to the KafkaListeners in the
  Infrastructure/Messages Layer) will be caught and rethrown wrapped in a `BatchListenerFailedException`.
- Spring handles the `BatchListenerFailedException` as follows:
    - Commit the offsets of the previous messages that have been processed successfully up until the error happened
    - Set the current offset so that all the remaining records in the batch (including the failed record) will be
      redelivered.
    - Delay delivery for a predefined length of time (= 'backoff')
    - If the maximum number of retries is exceeded without success, then the error is logged and the message is placed
      in the dead letter topic, which is given the same name as the incoming topic with suffix '-dlt'.
    - See
      [Spring-Kafka: Recovering Batch Error Handler](https://docs.spring.io/spring-kafka/docs/2.6.0/reference/html/#recovering-batch-eh)
- As before, all these scenarios need to be fully tested in integration and/or E2E tests, to prove that the state
  remains consistent, and no state gets lost.

#### Saga Pattern: Semantic Error Response Message

- As stated above, Saga flows are defined as a series of Request/Response messages, which are centrally controlled by
  an 'Orchestrator' microservice.
- Each Saga Request/Response interaction defines a set of Response messages that are used to communicate state back to
  the Orchestrator. E.g.
    - [payment_cancelled_response.avsc](../common/common-kafka/kafka-model/src/main/avro/payment_cancelled_response.avsc)
    - [payment_failed_response.avsc](../common/common-kafka/kafka-model/src/main/avro/payment_failed_response.avsc)
    - [payment_paid_response.avsc](../common/common-kafka/kafka-model/src/main/avro/payment_paid_response.avsc)
- An exception in a MessageListener should be caught and handled by sending back a correct semantic response,
  e.g. `PaymentFailedMessage`, which allows the Orchestrator to react and roll the Saga back accordingly.
- Again, all these scenarios need to be fully tested in integration and/or E2E tests, to prove that the state
  remains consistent, and no state gets lost.
