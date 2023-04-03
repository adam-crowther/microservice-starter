package com.acroteq.food.ordering.system.order.service.application.mapper;

import com.acroteq.food.ordering.system.order.service.application.model.Address;
import com.acroteq.food.ordering.system.order.service.domain.dto.create.OrderAddressDto;
import org.mapstruct.Mapper;

@Mapper
public interface OrderAddressDtoToApiMapper {

  Address convertDtoToApi(OrderAddressDto address);
}
