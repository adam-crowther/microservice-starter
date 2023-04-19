package com.acroteq.ticketing.airline.service.data.access.airline.adapter;

import com.acroteq.ticketing.airline.service.data.access.airline.entity.OrderApprovalJpaEntity;
import com.acroteq.ticketing.airline.service.data.access.airline.mapper.OrderApprovalDomainToJpaMapper;
import com.acroteq.ticketing.airline.service.data.access.airline.mapper.OrderApprovalJpaToDomainMapper;
import com.acroteq.ticketing.airline.service.data.access.airline.repository.OrderApprovalJpaRepository;
import com.acroteq.ticketing.airline.service.domain.entity.OrderApproval;
import com.acroteq.ticketing.airline.service.domain.ports.output.repository.OrderApprovalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OrderApprovalRepositoryImpl implements OrderApprovalRepository {

  private final OrderApprovalJpaRepository orderApprovalJpaRepository;
  private final OrderApprovalJpaToDomainMapper orderApprovalJpaToDomainMapper;
  private final OrderApprovalDomainToJpaMapper orderApprovalDomainToJpaMapper;

  @Override
  public OrderApproval save(final OrderApproval orderApproval) {
    final OrderApprovalJpaEntity entity = orderApprovalDomainToJpaMapper.convertDomainToEntity(orderApproval);
    final OrderApprovalJpaEntity savedEntity = orderApprovalJpaRepository.save(entity);
    return orderApprovalJpaToDomainMapper.convertEntityToDomain(savedEntity);
  }
}
