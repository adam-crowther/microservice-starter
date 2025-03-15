package com.acroteq.ticketing.order.service.data;

import com.acroteq.test.data.IdGenerator;
import com.acroteq.test.data.RandomInstantGenerator;
import com.acroteq.test.data.RandomLongGenerator;
import com.acroteq.ticketing.kafka.payment.avro.model.AuditRecord;
import com.acroteq.ticketing.kafka.payment.avro.model.PaymentPaidResponseMessage;
import com.acroteq.ticketing.kafka.payment.avro.model.PaymentRequestMessage;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
public class PaymentPaidResponseMessageGenerator {

  private final IdGenerator idGenerator;
  private final RandomLongGenerator randomLongGenerator;
  private final RandomInstantGenerator randomInstantGenerator;

  public PaymentPaidResponseMessage getPaymentPaidResponseMessage(final PaymentRequestMessage requestMessage) {

    final UUID sagaId = requestMessage.getSagaId();
    final long paymentId = idGenerator.nextId();
    final long paymentVersion = randomLongGenerator.getPositiveWithUpperBound(5);

    final Instant createdTimestamp = randomInstantGenerator.getPast();
    final Instant lastModifiedTimestamp = randomInstantGenerator.getPastAfter(createdTimestamp);
    final AuditRecord audit = new AuditRecord(createdTimestamp, lastModifiedTimestamp);

    final long customerId = requestMessage.getCustomerId();
    final long orderId = requestMessage.getOrderId();
    final long orderVersion = requestMessage.getOrderVersion();
    final String valueCurrencyId = requestMessage.getValueCurrencyId();
    final BigDecimal valueAmount = requestMessage.getValueAmount();

    return new PaymentPaidResponseMessage(sagaId,
                                          paymentId,
                                          paymentVersion,
                                          audit,
                                          customerId,
                                          orderId,
                                          orderVersion,
                                          valueCurrencyId,
                                          valueAmount);
  }
}
