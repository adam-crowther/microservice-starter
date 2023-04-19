package com.acroteq.ticketing.airline.service.domain;

import com.acroteq.ticketing.airline.service.domain.dto.AirlineApprovalRequestDto;
import com.acroteq.ticketing.airline.service.domain.entity.Order;
import com.acroteq.ticketing.airline.service.domain.entity.OrderApproval;
import com.acroteq.ticketing.airline.service.domain.event.OrderApprovalEvent;
import com.acroteq.ticketing.airline.service.domain.event.OrderRejectedEvent;
import com.acroteq.ticketing.airline.service.domain.exception.OrderApprovalSaveFailedException;
import com.acroteq.ticketing.airline.service.domain.mapper.AirlineRequestDtoToOrderDomainMapper;
import com.acroteq.ticketing.airline.service.domain.ports.output.repository.OrderApprovalRepository;
import com.acroteq.ticketing.airline.service.domain.valueobject.OrderApprovalOutput;
import com.acroteq.ticketing.domain.validation.ValidationResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
class AirlineApprovalRequestHelper {

  private final AirlineDomainService airlineDomainService;
  private final AirlineRequestDtoToOrderDomainMapper orderMapper;
  private final OrderApprovalRepository orderApprovalRepository;

  OrderApprovalEvent checkOrder(final AirlineApprovalRequestDto airlineApprovalRequest) {
    log.info("Checking airline approval for order id: {}", airlineApprovalRequest.getOrderId());
    final Order order = orderMapper.convertDtoToDomain(airlineApprovalRequest);

    final OrderApprovalOutput orderApprovalOutput = airlineDomainService.validateOrder(order);
    final OrderApproval orderApproval = orderApprovalOutput.orderApproval();
    final OrderApproval savedOrderApproval = orderApprovalRepository.save(orderApproval);
    if (savedOrderApproval == null) {
      throw new OrderApprovalSaveFailedException(orderApproval.getId());
    }

    final ValidationResult result = orderApprovalOutput.validationResult();
    return OrderRejectedEvent.builder()
                             .orderApproval(savedOrderApproval)
                             .result(result)
                             .build();
  }
}
