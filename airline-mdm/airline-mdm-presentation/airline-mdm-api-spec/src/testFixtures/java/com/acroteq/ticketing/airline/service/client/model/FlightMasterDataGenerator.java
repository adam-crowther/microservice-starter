package com.acroteq.ticketing.airline.service.client.model;

import com.acroteq.ticketing.test.data.RandomDoubleGenerator;
import com.acroteq.ticketing.test.data.RandomStringGenerator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FlightMasterDataGenerator {

  private final RandomStringGenerator randomStringGenerator;
  private final RandomDoubleGenerator randomDoubleGenerator;

  public CreateFlightCommand getFlight() {
    return getFlight(true);
  }

  public CreateFlightCommand getFlight(final boolean available) {
    final String flightNumber = randomStringGenerator.getRandomString();
    final String priceCurrencyId = "CHF";
    final Double priceAmount = randomDoubleGenerator.getBetween(100, 250);

    final CashValueCommand price = CashValueCommand.builder()
                                                   .amount(priceAmount)
                                                   .currencyId(priceCurrencyId)
                                                   .build();

    return CreateFlightCommand.builder()
                              .flightNumber(flightNumber)
                              .price(price)
                              .available(available)
                              .build();
  }
}
