package com.acroteq.ticketing.order.service.presentation.mapper;

import com.acroteq.ticketing.common.application.mapper.DtoToApiMapper;
import com.acroteq.ticketing.order.service.domain.dto.create.OrderAddressDto;
import com.acroteq.ticketing.order.service.presentation.model.Address;
import org.mapstruct.Mapper;

@Mapper
public interface OrderAddressDtoToApiMapper extends DtoToApiMapper<OrderAddressDto, Address> {

  @Override
  Address convertDtoToApi(OrderAddressDto address);
}
