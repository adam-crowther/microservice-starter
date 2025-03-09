package com.acroteq.ticketing.order.service.data;

import com.acroteq.ticketing.kafka.customer.avro.model.AuditRecord;
import com.acroteq.ticketing.kafka.customer.avro.model.CustomerEventMessage;
import com.acroteq.ticketing.test.data.IdGenerator;
import com.acroteq.ticketing.test.data.RandomBigDecimalGenerator;
import com.acroteq.ticketing.test.data.RandomInstantGenerator;
import com.acroteq.ticketing.test.data.RandomLongGenerator;
import com.acroteq.ticketing.test.data.RandomStringGenerator;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@RequiredArgsConstructor
public class CustomerMasterDataGenerator {

  private final IdGenerator idGenerator;
  private final RandomLongGenerator randomLongGenerator;
  private final RandomStringGenerator randomStringGenerator;
  private final RandomBigDecimalGenerator randomBigDecimalGenerator;
  private final RandomInstantGenerator randomInstantGenerator;

  public CustomerEventMessage getCustomerEventMessage() {
    final Long customerId = idGenerator.nextId();
    final Long customerVersion = randomLongGenerator.getPositiveWithUpperBound(5);
    final String customerUserName = randomStringGenerator.getRandomString();
    final String customerFirstName = randomStringGenerator.getRandomString();
    final String customerLastName = randomStringGenerator.getRandomString();
    final String customerCreditLimitCurrencyId = "CHF";
    final BigDecimal customerCreditLimitAmount = randomBigDecimalGenerator.getPositive();

    final Instant createdTimestamp = randomInstantGenerator.getPast();
    final Instant lastModifiedTimestamp = randomInstantGenerator.getPastAfter(createdTimestamp);
    final AuditRecord audit = new AuditRecord(createdTimestamp, lastModifiedTimestamp);

    return new CustomerEventMessage(customerId,
                                    customerVersion,
                                    audit,
                                    customerUserName,
                                    customerFirstName,
                                    customerLastName,
                                    customerCreditLimitCurrencyId,
                                    customerCreditLimitAmount);
  }
}
