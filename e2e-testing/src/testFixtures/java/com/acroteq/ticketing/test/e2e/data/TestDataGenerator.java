package com.acroteq.ticketing.test.e2e.data;

import com.acroteq.test.data.RandomDoubleGenerator;
import com.acroteq.test.data.RandomHolder;
import com.acroteq.test.data.RandomIntegerGenerator;
import com.acroteq.test.data.RandomStringGenerator;
import com.acroteq.ticketing.airline.service.client.model.AirlineMasterDataGenerator;
import com.acroteq.ticketing.airline.service.client.model.CreateAirlineCommand;
import com.acroteq.ticketing.airline.service.client.model.FlightMasterDataGenerator;
import com.acroteq.ticketing.customer.service.client.model.CreateCustomerCommand;
import com.acroteq.ticketing.customer.service.client.model.CustomerMasterDataGenerator;
import com.acroteq.ticketing.order.service.client.model.CreateOrderCommand;
import com.acroteq.ticketing.order.service.client.model.CreateOrderCommandGenerator;


public class TestDataGenerator {

  private final RandomHolder randomHolder = new RandomHolder();
  private final RandomIntegerGenerator randomIntegerGenerator = new RandomIntegerGenerator(randomHolder);
  private final RandomDoubleGenerator randomDoubleGenerator = new RandomDoubleGenerator(randomHolder);
  private final RandomStringGenerator randomStringGenerator = new RandomStringGenerator(randomIntegerGenerator);

  private final FlightMasterDataGenerator flightGenerator = new FlightMasterDataGenerator(randomStringGenerator,
                                                                                          randomDoubleGenerator);
  private final AirlineMasterDataGenerator airlineGenerator = new AirlineMasterDataGenerator(randomStringGenerator,
                                                                                             randomDoubleGenerator,
                                                                                             flightGenerator);
  private final CustomerMasterDataGenerator customerGenerator = new CustomerMasterDataGenerator(randomStringGenerator,
                                                                                                randomDoubleGenerator);
  private final CreateOrderCommandGenerator orderCommandGenerator = new CreateOrderCommandGenerator(
      randomStringGenerator);

  public CreateAirlineCommand getCreateAirlineCommand() {
    return airlineGenerator.getAirlineEventMessage();
  }

  public CreateAirlineCommand getCreateAirlineCommand(final Double flightPrice) {
    return airlineGenerator.getAirlineEventMessage(flightPrice);
  }

  public CreateAirlineCommand getCreateAirlineCommand(final boolean active,
                                                      final int flightCount,
                                                      final Double flightPrice) {
    return airlineGenerator.getAirlineEventMessage(active, flightCount, flightPrice);
  }

  public CreateCustomerCommand getCreateCustomerCommand(final Double creditLimit) {
    return customerGenerator.getCreateCustomerCommand(creditLimit);
  }


  public CreateCustomerCommand getCreateCustomerCommand() {
    return customerGenerator.getCreateCustomerCommand();
  }

  public CreateOrderCommand getCreateOrderCommand(final MasterData masterData,
                                                  final int quantity) {
    final Long customerId = masterData.getCustomerId();
    final Long airlineId = masterData.getAirlineId();
    final Long flightId = masterData.getFlightId();
    return orderCommandGenerator.getCreateOrderCommand(customerId, airlineId, flightId, quantity);
  }
}
