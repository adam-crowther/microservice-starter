package com.acroteq.ticketing.order.service.data;

import com.acroteq.ticketing.kafka.flight.approval.avro.model.AirlineApprovalApprovedResponseMessage;
import com.acroteq.ticketing.kafka.flight.approval.avro.model.AirlineApprovalRequestMessage;
import com.acroteq.ticketing.kafka.flight.approval.avro.model.AuditRecord;
import com.acroteq.ticketing.test.data.RandomInstantGenerator;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
public class AirlineApprovalApprovedResponseMessageGenerator {

  private final RandomInstantGenerator randomInstantGenerator;

  public AirlineApprovalApprovedResponseMessage getAirlineApprovalApprovedResponseMessage(final AirlineApprovalRequestMessage requestMessage) {

    final UUID sagaId = requestMessage.getSagaId();

    final long orderId = requestMessage.getOrderId();
    final long orderVersion = requestMessage.getOrderVersion();

    final Instant createdTimestamp = randomInstantGenerator.getPast();
    final Instant lastModifiedTimestamp = randomInstantGenerator.getPastAfter(createdTimestamp);
    final AuditRecord audit = new AuditRecord(createdTimestamp, lastModifiedTimestamp);

    final long airlineId = requestMessage.getAirlineId();

    return new AirlineApprovalApprovedResponseMessage(sagaId, orderId, orderVersion, audit, airlineId);
  }
}
