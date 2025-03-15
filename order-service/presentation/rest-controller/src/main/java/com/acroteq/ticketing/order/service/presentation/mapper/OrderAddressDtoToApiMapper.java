package com.acroteq.ticketing.order.service.presentation.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.common.application.mapper.DtoToApiMapper;
import com.acroteq.ticketing.order.service.domain.dto.create.OrderAddressDto;
import com.acroteq.ticketing.order.service.presentation.model.Address;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class)
public interface OrderAddressDtoToApiMapper extends DtoToApiMapper<OrderAddressDto, Address> {

  @Override
  Address convertDtoToApi(OrderAddressDto address);
}
