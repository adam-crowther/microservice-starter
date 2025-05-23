package com.acroteq.ticketing.order.service.data;

import com.acroteq.test.data.IdGenerator;
import com.acroteq.test.data.RandomInstantGenerator;
import com.acroteq.test.data.RandomLongGenerator;
import com.acroteq.test.data.RandomStringGenerator;
import com.acroteq.ticketing.kafka.airline.avro.model.AirlineEventMessage;
import com.acroteq.ticketing.kafka.airline.avro.model.AuditRecord;
import com.acroteq.ticketing.kafka.airline.avro.model.Flight;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class AirlineMasterDataGenerator {

  private final IdGenerator idGenerator;
  private final RandomLongGenerator randomLongGenerator;
  private final RandomStringGenerator randomStringGenerator;
  private final RandomInstantGenerator randomInstantGenerator;
  private final FlightMasterDataGenerator flightGenerator;

  public AirlineEventMessage getAirlineEventMessage() {
    return getAirlineEventMessage(true, 1);
  }

  public AirlineEventMessage getAirlineEventMessage(final boolean active, final int flightCount) {
    final Long airlineId = idGenerator.nextId();
    final Long airlineVersion = randomLongGenerator.getPositiveWithUpperBound(5);
    final String airlineCode = randomStringGenerator.getRandomString();
    final String airlineName = randomStringGenerator.getRandomString();

    final Instant createdTimestamp = randomInstantGenerator.getPast();
    final Instant lastModifiedTimestamp = randomInstantGenerator.getPastAfter(createdTimestamp);
    final AuditRecord audit = new AuditRecord(createdTimestamp, lastModifiedTimestamp);

    final List<Flight> flights = IntStream.range(0, flightCount)
                                          .mapToObj(count -> flightGenerator.getFlight())
                                          .toList();

    return new AirlineEventMessage(airlineId, airlineVersion, audit, airlineCode, airlineName, active, flights);
  }
}
