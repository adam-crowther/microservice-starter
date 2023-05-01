package com.acroteq.ticketing.approval.service.domain.processor;

import static com.acroteq.ticketing.domain.valueobject.OrderApprovalStatus.REJECTED;

import com.acroteq.ticketing.approval.service.domain.dto.AirlineApprovalRequestDto;
import com.acroteq.ticketing.approval.service.domain.entity.airline.Airline;
import com.acroteq.ticketing.approval.service.domain.entity.order.OrderApproval;
import com.acroteq.ticketing.approval.service.domain.event.order.OrderApprovalEvent;
import com.acroteq.ticketing.approval.service.domain.event.order.OrderRejectedEvent;
import com.acroteq.ticketing.approval.service.domain.resolver.AirlineResolver;
import com.acroteq.ticketing.domain.validation.ValidationResult;
import com.acroteq.ticketing.domain.valueobject.AirlineId;
import com.acroteq.ticketing.domain.valueobject.OrderId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class AirlineApprovalExceptionEventFactory {

  private final AirlineResolver airlineResolver;

  public OrderApprovalEvent createOrderRejectedEvent(final AirlineApprovalRequestDto airlineApprovalRequest,
                                                     final Exception exception) {
    final UUID sagaId = airlineApprovalRequest.getSagaId();
    final OrderId orderId = OrderId.of(airlineApprovalRequest.getOrderId());
    final AirlineId airlineId = AirlineId.of(airlineApprovalRequest.getAirlineId());

    final ValidationResult result = createValidationResult(exception, orderId);
    final OrderApproval orderApproval = createOrderApproval(orderId, airlineId);
    return OrderRejectedEvent.builder()
                             .sagaId(sagaId)
                             .result(result)
                             .orderApproval(orderApproval)
                             .build();
  }

  private ValidationResult createValidationResult(final Exception exception, final OrderId orderId) {
    return ValidationResult.fail("Exception while checking order with id %s: %s", orderId, exception.getMessage());
  }

  private OrderApproval createOrderApproval(final OrderId orderId, final AirlineId airlineId) {
    final Airline airline = airlineResolver.resolve(airlineId);
    return OrderApproval.builder()
                        .orderId(orderId)
                        .airline(airline)
                        .approvalStatus(REJECTED)
                        .build();
  }
}
