package com.acroteq.ticketing.airline.service.client.model;

import com.acroteq.test.data.RandomDoubleGenerator;
import com.acroteq.test.data.RandomStringGenerator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AirlineMasterDataGenerator {

  private final RandomStringGenerator randomStringGenerator;
  private final RandomDoubleGenerator randomDoubleGenerator;

  public CreateAirline getAirlineEventMessage() {
    return getAirlineEventMessage(true);
  }

  public CreateAirline getAirlineEventMessage(final boolean active) {
    final String airlineName = randomStringGenerator.getRandomString();
    final String airlineCode = randomStringGenerator.getRandomString();

    return CreateAirline.builder()
                        .active(active)
                        .name(airlineName)
                        .code(airlineCode)
                        .build();
  }
}

