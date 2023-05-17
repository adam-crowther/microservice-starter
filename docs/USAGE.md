# Installation and Operation

## Installation

If you want to start with a fresh and empty data constellation, delete the contents
of `infrastructure/docker-compose/build`.

|     |                                       | Shell script / Gradle task                                                    |
|----:|---------------------------------------|-------------------------------------------------------------------------------|
|     | Change directory to docker_compose.   | `$ cd infrastructure/docker-compose/`                                         |
|  1. | Start Zookeeper                       | `$ ./start_zookeeper.sh`                                                      |
|  2. | Start PostgreSQL                      | `$ ./start_postgres.sh`                                                       |
|  3. | Start Kafka                           | `$ ./start_kafka.sh`                                                          |
|  4. | Initialise Kafka topics               | `$ ./init_kafka.sh`                                                           |
|     | Change directory back to project root | `$ cd ../..`                                                                  |
|  5. | Start Airline MDM                     | `gradle :airline-mdm:airline-mdm-container:bootRun`                           |
|  6. | Start Airline MDM Swagger             | `gradle :airline-mdm:airline-mdm-presentation:airline-mdm-swagger:bootRun`    |
|  7. | Start Customer MDM                    | `gradle :customer-mdm:customer-mdm-container:bootRun`                         |
|  8. | Start Customer MDM Swagger            | `gradle :customer-mdm:customer-mdm-presentation:customer-mdm-swagger:bootRun` |
|  9. | Start Airline-Approval MDM Service    | `gradle :airline-approval:airline-approval-container:bootRun`                 |
| 10. | Start Payment Service                 | `gradle :payment-service:payment-container:bootRun`                           |
| 11. | Start Order Service                   | `gradle :order-service:order-container:bootRun`                               |
| 12. | Start Order Service Swagger           | `gradle :order-service:order-presentation:order-swagger:bootRun`              |

## Operation

### Create an airline with 2 flights

[POST `localhost:8183/airlines`](http://localhost:8283/index.html#operations-airlines-createAirline) with body:

```json
{
  "name": "British Airways",
  "active": true,
  "flights": [
    {
      "flightNumber": "BA123",
      "price": {
        "currencyId": "CHF",
        "amount": 990
      },
      "available": "true"
    },
    {
      "flightNumber": "BA456",
      "price": {
        "currencyId": "CHF",
        "amount": 750
      },
      "available": false
    }
  ]
}
```

The response will contain an airlineId.

- Log in to pgAdmin
- Check the `airline_master` schema.
    - You will see one new record in the `airlines` table
    - And two new records in the `flights` table
- Check the `airline_approval` schema.
    - You will see one new record in the `airlines` table
    - And two new records in the `flights` table
- Check the `orders` schema.
    - You will see one new record in the `airlines` table
    - And two new records in the `flights` table

The records in the `airline_master` schema were inserted by the airline-master-data management microservice directly,
through its REST API. The records in the `airline_approval` and `orders` schemas were inserted by the
`airline-approval-service` and the `order-service` respectively, via the `AirlineCreatedEvent`, which was sent through
Kafka. This is CQRS: we have a microservice which handles Commands and 2 which handle Queries.

Note that the schemas are different in each microservice schemas:  Each microservice only need to maintain the data that
it will actually need.

### Load the airline to get the flightIds.

[GET `localhost:8183/airlines/<airlineId>`](http://localhost:8283/index.html#operations-airlines-getAirlineById) with
body:

Output:

```json
{
  "id": 1,
  "name": "British Airways",
  "active": true,
  "flights": [
    {
      "id": 1,
      "flightNumber": "BA123",
      "price": {
        "currencyId": "CHF",
        "amount": 990.0
      },
      "available": true
    },
    {
      "id": 2,
      "flightNumber": "BA456",
      "price": {
        "currencyId": "CHF",
        "amount": 750.0
      },
      "available": true
    }
  ]
}
```

Note that the output includes the IDs of the new entities.

### Create a customer

[POST `localhost:8185/customers`](http://localhost:8285/index.html#operations-customers-createCustomer) with body:

```json
{
  "userName": "Chuck",
  "firstName": "Charlie",
  "lastName": "Brown",
  "creditLimitCurrencyId": "CHF",
  "creditLimitAmount": 2000.00
}
```

The response will contain a customerId.

- Log in to pgAdmin
- Check the `customer_master` schema.
    - You will see one new record in the `customers` table
- Check the `payment` schema.
    - You will see one new record in the `credit_balance` table
- Check the `orders` schema.
    - You will see one new record in the `customers` table

The records in the `customer_master` schema were inserted by the airline master data management microservice directly,
through its REST API. The records in the `payment` and `orders` schemas were inserted by the `payment-service` and
the `order-service` respectively via the `CustomerCreatedEvent`, which was sent through Kafka. CQRS again.

Again, note that the schemas are different in each microservice schemas:  Each microservice only need to maintain the
data that it will actually need.

### Load the customer

[GET `localhost:8185/customers/<customerId>`](http://localhost:8285/index.html#operations-customers-getCustomerById)
with body:

Output:

```json
{
  "id": 1,
  "userName": "Chuck",
  "firstName": "Charlie",
  "lastName": "Brown",
  "creditLimitCurrencyId": "CHF",
  "creditLimitAmount": 2000.0
}
```

### Create an order

[POST `localhost:8281/orders`](http://localhost:8281/index.html#operations-orders-createOrder) with body:

```json
{
  "customerId": 1,
  "airlineId": 1,
  "items": [
    {
      "flightId": 1,
      "quantity": 3
    }
  ],
  "address": {
    "street": "1770 James Street",
    "postalCode": "CO 80915",
    "city": "Colorado Springs"
  }
}
```

Output:

```json
{
  "trackingId": "c721bfe2-791c-454c-85cd-464d07aafe5f",
  "status": "PENDING",
  "message": "Order created successfully"
}
```

### Load the order

[GET `localhost:8281/orders/c721bfe2-791c-454c-85cd-464d07aafe5f`](http://localhost:8281/index.html#operations-orders-getOrderByTrackingId)
with body:

```json
{
  "trackingId": "c721bfe2-791c-454c-85cd-464d07aafe5f",
  "status": "APPROVED",
  "customerId": 1,
  "airlineId": 1,
  "items": [],
  "address": {
    "street": "1770 James Street",
    "postalCode": "CO 80915",
    "city": "Colorado Springs"
  }
}
```

Note that the status of the order has changed. In the response from the POST `localhost:8181/orders`, the `orderStatus`
was `PENDING`. In the output from the GET request, the `orderStatus` has changed, and is now `APPROVED`.

In the background, `order-service` has communicated with `payment-service` and `airline-approval-service` through a
Saga, to get the payment processed and to get approval for the ticket sale. The individual steps are:

- `order-service` sent a `PaymentRequestMessage` which was consumed by the `payment-service`.
- `payment-service` processed the payment and assigned `paymentStatus = COMPLETED`.
- `payment-service` sent a `PaymentResponseMessage` which was consumed by `order-service`.
- `order-service` set the orderStatus to `PAID`.
- `order-service` sent a `AirlineApprovalRequestMessage` which was consumed by the `airline-approval-service`.
- `airline-approval-service` checked the order and assigned orderApprovalStatus = `APPROVED`.
- `airline-approval-service` sent a `AirlineApprovalResponseMessage` which was consumed by `order-service`.
- `order-service` set the orderStatus to orderStatus `PAID`.