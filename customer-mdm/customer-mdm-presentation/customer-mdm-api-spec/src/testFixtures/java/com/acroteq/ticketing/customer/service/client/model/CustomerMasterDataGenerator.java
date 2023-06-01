package com.acroteq.ticketing.customer.service.client.model;

import com.acroteq.ticketing.test.data.RandomDoubleGenerator;
import com.acroteq.ticketing.test.data.RandomStringGenerator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomerMasterDataGenerator {

  private final RandomStringGenerator randomStringGenerator;
  private final RandomDoubleGenerator randomDoubleGenerator;

  public CreateCustomerCommand getCreateCustomerCommand() {
    final String customerUserName = randomStringGenerator.getRandomString();
    final String customerFirstName = randomStringGenerator.getRandomString();
    final String customerLastName = randomStringGenerator.getRandomString();
    final String customerCreditLimitCurrencyId = "CHF";
    final double customerCreditLimitAmount = randomDoubleGenerator.getPositive();

    return CreateCustomerCommand.builder()
                                .userName(customerUserName)
                                .firstName(customerFirstName)
                                .lastName(customerLastName)
                                .creditLimitCurrencyId(customerCreditLimitCurrencyId)
                                .creditLimitAmount(customerCreditLimitAmount)
                                .build();
  }
}
