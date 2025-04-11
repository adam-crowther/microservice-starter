package com.acroteq.ticketing.test.e2e.data;

import com.acroteq.test.data.RandomDoubleGenerator;
import com.acroteq.test.data.RandomHolder;
import com.acroteq.test.data.RandomIntegerGenerator;
import com.acroteq.test.data.RandomStringGenerator;
import com.acroteq.ticketing.airline.service.client.model.AirlineMasterDataGenerator;
import com.acroteq.ticketing.airline.service.client.model.CreateAirline;
import com.acroteq.ticketing.airline.service.client.model.CreateFlight;
import com.acroteq.ticketing.airline.service.client.model.FlightMasterDataGenerator;
import com.acroteq.ticketing.customer.service.client.model.CreateCustomer;
import com.acroteq.ticketing.customer.service.client.model.CustomerMasterDataGenerator;
import com.acroteq.ticketing.order.service.client.model.CreateOrder;
import com.acroteq.ticketing.order.service.client.model.CreateOrderGenerator;


public class TestDataGenerator {

  private final RandomHolder randomHolder = new RandomHolder();
  private final RandomIntegerGenerator randomIntegerGenerator = new RandomIntegerGenerator(randomHolder);
  private final RandomDoubleGenerator randomDoubleGenerator = new RandomDoubleGenerator(randomHolder);
  private final RandomStringGenerator randomStringGenerator = new RandomStringGenerator(randomIntegerGenerator);

  private final FlightMasterDataGenerator flightGenerator = new FlightMasterDataGenerator(randomStringGenerator,
                                                                                          randomDoubleGenerator);
  private final AirlineMasterDataGenerator airlineGenerator = new AirlineMasterDataGenerator(randomStringGenerator,
                                                                                             randomDoubleGenerator);
  private final CustomerMasterDataGenerator customerGenerator = new CustomerMasterDataGenerator(randomStringGenerator,
                                                                                                randomDoubleGenerator);
  private final CreateOrderGenerator orderGenerator = new CreateOrderGenerator(randomStringGenerator);

  public CreateAirline getCreateAirline() {
    return airlineGenerator.getAirlineEventMessage();
  }

  public CreateAirline getCreateAirline(final boolean active) {
    return airlineGenerator.getAirlineEventMessage(active);
  }

  public CreateFlight getCreateFlight() {
    return flightGenerator.getFlight();
  }

  public CreateFlight getCreateFlight(final Double flightPrice) {
    return flightGenerator.getFlight(flightPrice);
  }

  public CreateCustomer getCreateCustomer(final Double creditLimit) {
    return customerGenerator.getCreateCustomer(creditLimit);
  }


  public CreateCustomer getCreateCustomer() {
    return customerGenerator.getCreateCustomer();
  }

  public CreateOrder getCreateOrder(final MasterData masterData, final int quantity) {
    final Long customerId = masterData.getCustomerId();
    final Long airlineId = masterData.getAirlineId();
    final Long flightId = masterData.getFlightId();
    return orderGenerator.getCreateOrder(customerId, airlineId, flightId, quantity);
  }
}
