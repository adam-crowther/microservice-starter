package com.acroteq.ticketing.order.service.data;

import com.acroteq.ticketing.kafka.airline.avro.model.AirlineEventMessage;
import com.acroteq.ticketing.kafka.customer.avro.model.CustomerEventMessage;
import com.acroteq.ticketing.kafka.flight.approval.avro.model.AirlineApprovalApprovedResponseMessage;
import com.acroteq.ticketing.kafka.flight.approval.avro.model.AirlineApprovalRequestMessage;
import com.acroteq.ticketing.kafka.payment.avro.model.PaymentPaidResponseMessage;
import com.acroteq.ticketing.kafka.payment.avro.model.PaymentRequestMessage;
import com.acroteq.ticketing.order.service.client.model.CreateOrderCommand;
import com.acroteq.ticketing.order.service.client.model.CreateOrderCommandGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class TestDataGenerator {

  private final AirlineMasterDataGenerator airlineGenerator;
  private final CustomerMasterDataGenerator customerGenerator;
  private final CreateOrderCommandGenerator orderCommandGenerator;
  private final PaymentPaidResponseMessageGenerator paymentPaidResponseGenerator;
  private final AirlineApprovalApprovedResponseMessageGenerator airlineApprovedResponseGenerator;

  public AirlineEventMessage getAirlineEventMessage() {
    return airlineGenerator.getAirlineEventMessage();
  }

  public AirlineEventMessage getAirlineEventMessage(final boolean active, final int flightCount) {
    return airlineGenerator.getAirlineEventMessage(active, flightCount);
  }

  public CustomerEventMessage getCustomerEventMessage() {
    return customerGenerator.getCustomerEventMessage();
  }

  public CreateOrderCommand getCreateOrderCommand(final long customerId,
                                                  final long airlineId,
                                                  final long flightId,
                                                  final int quantity) {
    return orderCommandGenerator.getCreateOrderCommand(customerId, airlineId, flightId, quantity);
  }

  public PaymentPaidResponseMessage getPaymentPaidResponseMessage(final PaymentRequestMessage requestMessage) {
    return paymentPaidResponseGenerator.getPaymentPaidResponseMessage(requestMessage);
  }

  public AirlineApprovalApprovedResponseMessage getAirlineApprovalApprovedResponseMessage(final AirlineApprovalRequestMessage requestMessage) {
    return airlineApprovedResponseGenerator.getAirlineApprovalApprovedResponseMessage(requestMessage);
  }
}
