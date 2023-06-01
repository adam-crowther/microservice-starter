package com.acroteq.ticketing.test.e2e.data;

import com.acroteq.ticketing.airline.service.client.model.AirlineMasterDataGenerator;
import com.acroteq.ticketing.airline.service.client.model.CreateAirlineCommand;
import com.acroteq.ticketing.airline.service.client.model.FlightMasterDataGenerator;
import com.acroteq.ticketing.customer.service.client.model.CreateCustomerCommand;
import com.acroteq.ticketing.customer.service.client.model.CustomerMasterDataGenerator;
import com.acroteq.ticketing.order.service.client.model.CreateOrderCommand;
import com.acroteq.ticketing.order.service.client.model.CreateOrderCommandGenerator;
import com.acroteq.ticketing.test.data.RandomDoubleGenerator;
import com.acroteq.ticketing.test.data.RandomHolder;
import com.acroteq.ticketing.test.data.RandomInstantGenerator;
import com.acroteq.ticketing.test.data.RandomIntegerGenerator;
import com.acroteq.ticketing.test.data.RandomStringGenerator;


public class TestDataGenerator {

  private final RandomHolder randomHolder = new RandomHolder();
  private final RandomIntegerGenerator randomIntegerGenerator = new RandomIntegerGenerator(randomHolder);
  private final RandomDoubleGenerator randomDoubleGenerator = new RandomDoubleGenerator(randomHolder);
  private final RandomStringGenerator randomStringGenerator = new RandomStringGenerator(randomIntegerGenerator);
  private final RandomInstantGenerator randomInstantGenerator = new RandomInstantGenerator(randomIntegerGenerator);

  private final FlightMasterDataGenerator flightGenerator = new FlightMasterDataGenerator(randomStringGenerator,
                                                                                          randomDoubleGenerator);
  private final AirlineMasterDataGenerator airlineGenerator = new AirlineMasterDataGenerator(randomStringGenerator,
                                                                                             randomInstantGenerator,
                                                                                             flightGenerator);
  private final CustomerMasterDataGenerator customerGenerator = new CustomerMasterDataGenerator(randomStringGenerator,
                                                                                                randomDoubleGenerator);
  private final CreateOrderCommandGenerator orderCommandGenerator = new CreateOrderCommandGenerator(
      randomStringGenerator);

  public CreateAirlineCommand getCreateAirlineCommand() {
    return airlineGenerator.getAirlineEventMessage();
  }

  public CreateAirlineCommand getCreateAirlineCommand(final boolean active, final int flightCount) {
    return airlineGenerator.getAirlineEventMessage(active, flightCount);
  }

  public CreateCustomerCommand getCreateCustomerCommand() {
    return customerGenerator.getCreateCustomerCommand();
  }

  public CreateOrderCommand getCreateOrderCommand(final long customerId,
                                                  final long airlineId,
                                                  final long flightId,
                                                  final int quantity) {
    return orderCommandGenerator.getCreateOrderCommand(customerId, airlineId, flightId, quantity);
  }
}
