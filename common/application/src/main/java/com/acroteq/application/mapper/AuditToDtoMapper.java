package com.acroteq.application.mapper;

import com.acroteq.application.dto.AuditDto;
import com.acroteq.domain.valueobject.Audit;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class)
public interface AuditToDtoMapper {
  AuditDto convertAuditToDto(Audit audit);
}
