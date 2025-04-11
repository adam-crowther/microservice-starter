package com.acroteq.ticketing.order.service.presentation.mapper;

import com.acroteq.application.mapper.DateTimeMapper;
import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.domain.valueobject.Audit;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class, uses = DateTimeMapper.class)
public interface AuditMapper {

  com.acroteq.ticketing.order.service.presentation.model.Audit convert(Audit audit);
}
