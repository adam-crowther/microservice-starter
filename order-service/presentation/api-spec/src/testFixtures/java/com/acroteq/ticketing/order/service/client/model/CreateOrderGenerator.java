package com.acroteq.ticketing.order.service.client.model;

import com.acroteq.test.data.RandomStringGenerator;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CreateOrderGenerator {

  private final RandomStringGenerator randomStringGenerator;

  public CreateOrder getCreateOrder(
      final long customerId, final long airlineId, final long flightId,
      final int quantity) {
    final String street = randomStringGenerator.getRandomString(50);
    final String city = randomStringGenerator.getRandomString(50);
    final String postalCode = randomStringGenerator.getRandomString(10);

    final CreateAddress address = CreateAddress.builder()
                                               .street(street)
                                               .city(city)
                                               .postalCode(postalCode)
                                               .build();
    final CreateOrderItem item = CreateOrderItem.builder()
                                                .flightId(flightId)
                                                .quantity(quantity)
                                                .build();
    return CreateOrder.builder()
                      .customerId(customerId)
                      .airlineId(airlineId)
                      .items(List.of(item))
                      .address(address)
                      .build();
  }

}
