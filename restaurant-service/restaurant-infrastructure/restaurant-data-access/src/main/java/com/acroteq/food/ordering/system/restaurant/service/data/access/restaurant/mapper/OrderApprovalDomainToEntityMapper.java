package com.acroteq.food.ordering.system.restaurant.service.data.access.restaurant.mapper;

import com.acroteq.food.ordering.system.common.application.mapper.IdMapper;
import com.acroteq.food.ordering.system.restaurant.service.data.access.restaurant.entity.OrderApprovalEntity;
import com.acroteq.food.ordering.system.restaurant.service.domain.entity.OrderApproval;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { IdMapper.class, OrderApprovalIdMapper.class })
public interface OrderApprovalDomainToEntityMapper {

    @Mapping(target = "status", source = "approvalStatus")
    OrderApprovalEntity convertDomainToEntity(OrderApproval orderApproval);
}
