package com.acroteq.ticketing.airline.service.presentation.mapper;

import com.acroteq.application.mapper.DateTimeMapper;
import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.domain.valueobject.Audit;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class, uses = DateTimeMapper.class)
public interface AuditMapper {

  com.acroteq.ticketing.airline.service.presentation.model.Audit convert(Audit audit);
}
