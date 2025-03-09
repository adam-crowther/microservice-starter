package com.acroteq.ticketing.order.service.presentation.mapper;

import com.acroteq.ticketing.application.dto.AuditDto;
import com.acroteq.ticketing.application.mapper.DateTimeMapper;
import com.acroteq.ticketing.application.mapper.MapstructConfig;
import com.acroteq.ticketing.order.service.presentation.model.Audit;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class, uses = DateTimeMapper.class)
public interface AuditDtoToApiMapper {

  Audit convertDtoToApi(AuditDto dto);
}
