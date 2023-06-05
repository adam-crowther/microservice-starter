package com.acroteq.ticketing.airline.service.client.model;

import com.acroteq.ticketing.test.data.RandomDoubleGenerator;
import com.acroteq.ticketing.test.data.RandomStringGenerator;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class AirlineMasterDataGenerator {

  private final RandomStringGenerator randomStringGenerator;
  private final RandomDoubleGenerator randomDoubleGenerator;
  private final FlightMasterDataGenerator flightGenerator;

  public CreateAirlineCommand getAirlineEventMessage() {
    final Double flightPrice = randomDoubleGenerator.getBetween(100, 250);
    return getAirlineEventMessage(true, 1, flightPrice);
  }

  public CreateAirlineCommand getAirlineEventMessage(final Double flightPrice) {
    return getAirlineEventMessage(true, 1, flightPrice);
  }

  public CreateAirlineCommand getAirlineEventMessage(final boolean active,
                                                     final int flightCount,
                                                     final Double flightPrice) {
    final String airlineName = randomStringGenerator.getRandomString();

    final List<CreateFlightCommand> flights = IntStream.range(0, flightCount)
                                                       .mapToObj(getFlight(flightPrice))
                                                       .toList();

    return CreateAirlineCommand.builder()
                               .active(active)
                               .name(airlineName)
                               .flights(flights)
                               .build();
  }

  private IntFunction<CreateFlightCommand> getFlight(final Double flightPrice) {
    return count -> flightGenerator.getFlight(flightPrice);
  }
}

