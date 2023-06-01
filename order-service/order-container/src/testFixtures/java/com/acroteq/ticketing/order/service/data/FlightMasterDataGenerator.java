package com.acroteq.ticketing.order.service.data;

import com.acroteq.ticketing.kafka.airline.avro.model.Flight;
import com.acroteq.ticketing.test.data.IdGenerator;
import com.acroteq.ticketing.test.data.RandomBigDecimalGenerator;
import com.acroteq.ticketing.test.data.RandomLongGenerator;
import com.acroteq.ticketing.test.data.RandomStringGenerator;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class FlightMasterDataGenerator {

  private final IdGenerator idGenerator;
  private final RandomLongGenerator randomLongGenerator;
  private final RandomStringGenerator randomStringGenerator;
  private final RandomBigDecimalGenerator randomBigDecimalGenerator;

  public Flight getFlight() {
    return getFlight(true);
  }

  public Flight getFlight(final boolean available) {
    final Long flightId = idGenerator.nextId();
    final Long flightVersion = randomLongGenerator.getPositiveWithUpperBound(5);
    final String flightNumber = randomStringGenerator.getRandomString();
    final String priceCurrencyId = "CHF";
    final BigDecimal priceAmount = randomBigDecimalGenerator.getBetween(100, 250);

    return new Flight(flightId, flightVersion, flightNumber, priceCurrencyId, priceAmount, available);
  }
}
