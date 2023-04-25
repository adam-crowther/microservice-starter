package com.acroteq.ticketing.order.service.presentation.mapper;

import com.acroteq.ticketing.order.service.domain.dto.create.OrderAddressDto;
import com.acroteq.ticketing.order.service.presentation.model.Address;
import org.mapstruct.Mapper;

@Mapper
public interface OrderAddressDtoToApiMapper {

  Address convertDtoToApi(OrderAddressDto address);
}
