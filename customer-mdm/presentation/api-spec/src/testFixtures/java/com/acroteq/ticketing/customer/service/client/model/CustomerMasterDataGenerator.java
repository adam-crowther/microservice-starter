package com.acroteq.ticketing.customer.service.client.model;

import com.acroteq.test.data.RandomDoubleGenerator;
import com.acroteq.test.data.RandomStringGenerator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomerMasterDataGenerator {

  private final RandomStringGenerator randomStringGenerator;
  private final RandomDoubleGenerator randomDoubleGenerator;

  public CreateCustomer getCreateCustomer() {
    final double creditLimit = randomDoubleGenerator.getPositive();

    return getCreateCustomer(creditLimit);
  }

  public CreateCustomer getCreateCustomer(final double creditLimit) {
    final String customerUserName = randomStringGenerator.getRandomString();
    final String customerFirstName = randomStringGenerator.getRandomString();
    final String customerLastName = randomStringGenerator.getRandomString();
    final String customerCreditLimitCurrencyId = "CHF";

    return CreateCustomer.builder()
                         .userName(customerUserName)
                         .firstName(customerFirstName)
                         .lastName(customerLastName)
                         .creditLimitCurrencyId(customerCreditLimitCurrencyId)
                         .creditLimitAmount(creditLimit)
                         .build();
  }
}
