package com.acroteq.ticketing.order.service.presentation.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.ticketing.order.service.domain.valueobject.StreetAddress;
import com.acroteq.ticketing.order.service.presentation.model.Address;
import com.acroteq.ticketing.order.service.presentation.model.CreateAddress;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class)
public interface AddressMapper {

  StreetAddress convert(CreateAddress address);

  Address convert(StreetAddress address);
}
