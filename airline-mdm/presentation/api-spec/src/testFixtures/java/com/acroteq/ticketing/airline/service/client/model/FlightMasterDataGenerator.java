package com.acroteq.ticketing.airline.service.client.model;

import com.acroteq.test.data.RandomDoubleGenerator;
import com.acroteq.test.data.RandomStringGenerator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FlightMasterDataGenerator {

  private final RandomStringGenerator randomStringGenerator;
  private final RandomDoubleGenerator randomDoubleGenerator;

  public CreateFlightCommand getFlight() {
    final Double price = randomDoubleGenerator.getBetween(100, 250);
    return getFlight(true, price);
  }

  public CreateFlightCommand getFlight(final Double priceAmount) {
    return getFlight(true, priceAmount);
  }

  public CreateFlightCommand getFlight(final boolean available) {
    final Double price = randomDoubleGenerator.getBetween(100, 250);
    return getFlight(available, price);
  }

  public CreateFlightCommand getFlight(final boolean available, final Double priceAmount) {
    final String flightNumber = randomStringGenerator.getRandomString();
    final String priceCurrencyId = "CHF";

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
