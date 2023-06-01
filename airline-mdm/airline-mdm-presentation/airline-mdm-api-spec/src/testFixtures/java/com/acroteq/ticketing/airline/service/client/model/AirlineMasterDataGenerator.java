package com.acroteq.ticketing.airline.service.client.model;

import com.acroteq.ticketing.test.data.RandomInstantGenerator;
import com.acroteq.ticketing.test.data.RandomStringGenerator;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class AirlineMasterDataGenerator {

  private final RandomStringGenerator randomStringGenerator;
  private final RandomInstantGenerator randomInstantGenerator;
  private final FlightMasterDataGenerator flightGenerator;

  public CreateAirlineCommand getAirlineEventMessage() {
    return getAirlineEventMessage(true, 1);
  }

  public CreateAirlineCommand getAirlineEventMessage(final boolean active, final int flightCount) {
    final String airlineName = randomStringGenerator.getRandomString();
    final Instant createdTimestamp = randomInstantGenerator.getPast();
    final Instant lastModifiedTimestamp = randomInstantGenerator.getPastAfter(createdTimestamp);

    final List<CreateFlightCommand> flights = IntStream.range(0, flightCount)
                                                       .mapToObj(count -> flightGenerator.getFlight())
                                                       .toList();

    return CreateAirlineCommand.builder()
                               .active(active)
                               .name(airlineName)
                               .flights(flights)
                               .build();
  }
}

