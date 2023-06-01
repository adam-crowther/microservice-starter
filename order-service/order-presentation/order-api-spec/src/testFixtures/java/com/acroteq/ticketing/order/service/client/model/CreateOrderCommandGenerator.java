package com.acroteq.ticketing.order.service.client.model;

import com.acroteq.ticketing.test.data.RandomStringGenerator;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CreateOrderCommandGenerator {

  private final RandomStringGenerator randomStringGenerator;

  public CreateOrderCommand getCreateOrderCommand(final long customerId,
                                                  final long airlineId,
                                                  final long flightId,
                                                  final int quantity) {
    final String street = randomStringGenerator.getRandomString();
    final String city = randomStringGenerator.getRandomString();
    final String postalCode = randomStringGenerator.getRandomString();

    final CreateAddressCommand address = CreateAddressCommand.builder()
                                                             .street(street)
                                                             .city(city)
                                                             .postalCode(postalCode)
                                                             .build();
    final CreateOrderItemCommand item = CreateOrderItemCommand.builder()
                                                              .flightId(flightId)
                                                              .quantity(quantity)
                                                              .build();
    return CreateOrderCommand.builder()
                             .customerId(customerId)
                             .airlineId(airlineId)
                             .items(List.of(item))
                             .address(address)
                             .build();
  }

}
