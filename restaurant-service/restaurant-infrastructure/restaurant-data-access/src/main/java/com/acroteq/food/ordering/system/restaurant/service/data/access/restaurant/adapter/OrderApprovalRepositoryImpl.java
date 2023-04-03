package com.acroteq.food.ordering.system.restaurant.service.data.access.restaurant.adapter;

import com.acroteq.food.ordering.system.restaurant.service.data.access.restaurant.entity.OrderApprovalEntity;
import com.acroteq.food.ordering.system.restaurant.service.data.access.restaurant.mapper.OrderApprovalDomainToEntityMapper;
import com.acroteq.food.ordering.system.restaurant.service.data.access.restaurant.mapper.OrderApprovalEntityToDomainMapper;
import com.acroteq.food.ordering.system.restaurant.service.data.access.restaurant.repository.OrderApprovalJpaRepository;
import com.acroteq.food.ordering.system.restaurant.service.domain.entity.OrderApproval;
import com.acroteq.food.ordering.system.restaurant.service.domain.ports.output.repository.OrderApprovalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OrderApprovalRepositoryImpl implements OrderApprovalRepository {

  private final OrderApprovalJpaRepository orderApprovalJpaRepository;
  private final OrderApprovalEntityToDomainMapper orderApprovalEntityToDomainMapper;
  private final OrderApprovalDomainToEntityMapper orderApprovalDomainToEntityMapper;

  @Override
  public OrderApproval save(final OrderApproval orderApproval) {
    final OrderApprovalEntity entity = orderApprovalDomainToEntityMapper.convertDomainToEntity(orderApproval);
    final OrderApprovalEntity savedEntity = orderApprovalJpaRepository.save(entity);
    return orderApprovalEntityToDomainMapper.convertEntityToDomain(savedEntity);
  }
}
