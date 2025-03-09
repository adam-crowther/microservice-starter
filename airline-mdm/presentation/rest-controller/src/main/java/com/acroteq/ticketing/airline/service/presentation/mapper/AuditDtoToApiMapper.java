package com.acroteq.ticketing.airline.service.presentation.mapper;

import com.acroteq.ticketing.airline.service.presentation.model.Audit;
import com.acroteq.ticketing.application.dto.AuditDto;
import com.acroteq.ticketing.application.mapper.DateTimeMapper;
import com.acroteq.ticketing.application.mapper.MapstructConfig;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class, uses = DateTimeMapper.class)
public interface AuditDtoToApiMapper {

  Audit convertDtoToApi(AuditDto dto);
}
