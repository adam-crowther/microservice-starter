package com.acroteq.ticketing.airline.service.presentation.mapper;

import com.acroteq.application.dto.AuditDto;
import com.acroteq.application.mapper.DateTimeMapper;
import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.ticketing.airline.service.presentation.model.Audit;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class, uses = DateTimeMapper.class)
public interface AuditDtoToApiMapper {

  Audit convertDtoToApi(AuditDto dto);
}
