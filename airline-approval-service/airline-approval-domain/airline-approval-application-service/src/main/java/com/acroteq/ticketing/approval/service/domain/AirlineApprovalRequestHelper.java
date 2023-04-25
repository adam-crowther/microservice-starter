package com.acroteq.ticketing.approval.service.domain;

import com.acroteq.ticketing.approval.service.domain.dto.AirlineApprovalRequestDto;
import com.acroteq.ticketing.approval.service.domain.entity.order.Order;
import com.acroteq.ticketing.approval.service.domain.entity.order.OrderApproval;
import com.acroteq.ticketing.approval.service.domain.event.order.OrderApprovalEvent;
import com.acroteq.ticketing.approval.service.domain.event.order.OrderRejectedEvent;
import com.acroteq.ticketing.approval.service.domain.mapper.OrderRequestDtoToOrderDomainMapper;
import com.acroteq.ticketing.approval.service.domain.ports.output.repository.OrderApprovalRepository;
import com.acroteq.ticketing.approval.service.domain.valueobject.OrderApprovalOutput;
import com.acroteq.ticketing.domain.validation.ValidationResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
class AirlineApprovalRequestHelper {

  private final AirlineDomainService airlineDomainService;
  private final OrderRequestDtoToOrderDomainMapper orderMapper;
  private final OrderApprovalRepository orderApprovalRepository;

  OrderApprovalEvent checkOrder(final AirlineApprovalRequestDto request) {
    log.info("Checking airline approval for order id: {}", request.getOrderId());
    final Order order = orderMapper.convertDtoToDomain(request);

    final OrderApprovalOutput orderApprovalOutput = airlineDomainService.validateOrder(order);
    final OrderApproval orderApproval = orderApprovalOutput.getOrderApproval();

    final ValidationResult result = orderApprovalOutput.getValidationResult();
    final UUID sagaId = request.getSagaId();
    final var builder = OrderRejectedEvent.builder()
                                          .sagaId(sagaId)
                                          .result(result);
    if (result.isPass()) {
      final OrderApproval savedOrderApproval = orderApprovalRepository.save(orderApproval);
      builder.orderApproval(savedOrderApproval);
    } else {
      builder.orderApproval(orderApproval);
    }

    return builder.build();
  }
}
