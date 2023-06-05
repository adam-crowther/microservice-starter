package com.acroteq.ticketing.test.e2e.data;


import com.acroteq.ticketing.airline.service.client.model.Airline;
import com.acroteq.ticketing.airline.service.client.model.Flight;
import com.acroteq.ticketing.customer.service.client.model.Customer;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;
import java.util.Optional;

@Value
@Builder
public class MasterData {

  @NonNull
  Customer customer;
  @NonNull
  Airline airline;

  public Long getCustomerId() {
    return customer.getId();
  }

  public Long getAirlineId() {
    return airline.getId();
  }

  public Long getFlightId() {
    return Optional.of(airline)
                   .map(Airline::getFlights)
                   .stream()
                   .flatMap(List::stream)
                   .map(Flight::getId)
                   .findFirst()
                   .orElseThrow();
  }

}
