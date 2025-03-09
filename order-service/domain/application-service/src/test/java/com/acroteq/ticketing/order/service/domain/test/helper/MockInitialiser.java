package com.acroteq.ticketing.order.service.domain.test.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class MockInitialiser {

  @Autowired
  private CustomerTestDataHelper customerTestDataHelper;
  @Autowired
  private OrderTestDataHelper orderTestDataHelper;
  @Autowired
  private AirlineTestDataHelper airlineTestDataHelper;
  @Autowired
  private FlightTestDataHelper flightTestDataHelper;

  public void initialiseMocks() {
    customerTestDataHelper.initialiseMocks();
    orderTestDataHelper.initialiseMocks();
    airlineTestDataHelper.initialiseMocks();
    flightTestDataHelper.initialiseMocks();
  }
}



