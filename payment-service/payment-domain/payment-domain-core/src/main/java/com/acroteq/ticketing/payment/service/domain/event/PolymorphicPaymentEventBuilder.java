package com.acroteq.ticketing.payment.service.domain.event;

import static com.acroteq.ticketing.domain.valueobject.PaymentStatus.CANCELLED;
import static com.acroteq.ticketing.domain.valueobject.PaymentStatus.COMPLETED;
import static com.acroteq.ticketing.domain.valueobject.PaymentStatus.FAILED;
import static com.google.common.base.Preconditions.checkNotNull;
import static lombok.AccessLevel.PACKAGE;

import com.acroteq.ticketing.domain.validation.ValidationResult;
import com.acroteq.ticketing.domain.valueobject.PaymentStatus;
import com.acroteq.ticketing.payment.service.domain.entity.Payment;
import com.acroteq.ticketing.payment.service.domain.exception.UnexpectedPaymentStatusException;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * Returns the correct event type based on the status of the given payment.
 */
@SuppressWarnings("PMD.AvoidFieldNameMatchingMethodName")
@NoArgsConstructor(access = PACKAGE)
public class PolymorphicPaymentEventBuilder {

  private UUID sagaId;
  private Payment payment;
  private ValidationResult result;

  private final Map<PaymentStatus, Supplier<PaymentEvent>> builders = Map.of(COMPLETED,
                                                                             this::buildPaymentCompletedEvent,
                                                                             CANCELLED,
                                                                             this::buildPaymentCancelledEvent,
                                                                             FAILED,
                                                                             this::buildPaymentFailedEvent);

  public PolymorphicPaymentEventBuilder sagaId(final UUID sagaId) {
    this.sagaId = sagaId;
    return this;
  }

  public PolymorphicPaymentEventBuilder payment(final Payment payment) {
    this.payment = payment;
    return this;
  }

  public PolymorphicPaymentEventBuilder result(final ValidationResult result) {
    this.result = result;
    return this;
  }

  public PaymentEvent build() {
    checkNotNull(sagaId, "sagaId must be set.");
    checkNotNull(payment, "savedPayment must be set.");
    checkNotNull(result, "result must be set.");

    final PaymentStatus paymentStatus = payment.getPaymentStatus();
    return Optional.of(paymentStatus)
                   .map(builders::get)
                   .map(Supplier::get)
                   .orElseThrow(() -> new UnexpectedPaymentStatusException(paymentStatus));
  }

  private PaymentEvent buildPaymentCompletedEvent() {
    return PaymentCompletedEvent.builder()
                                .sagaId(sagaId)
                                .payment(payment)
                                .result(result)
                                .build();
  }

  private PaymentEvent buildPaymentFailedEvent() {
    return PaymentFailedEvent.builder()
                             .sagaId(sagaId)
                             .payment(payment)
                             .result(result)
                             .build();
  }

  private PaymentEvent buildPaymentCancelledEvent() {
    return PaymentCancelledEvent.builder()
                                .sagaId(sagaId)
                                .payment(payment)
                                .result(result)
                                .build();
  }
}
