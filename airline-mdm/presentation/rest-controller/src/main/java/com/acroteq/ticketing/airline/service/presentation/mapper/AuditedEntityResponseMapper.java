package com.acroteq.ticketing.airline.service.presentation.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.common.application.mapper.EntityToApiMapper;
import com.acroteq.domain.entity.AggregateRoot;
import com.acroteq.domain.valueobject.EntityId;
import com.acroteq.ticketing.airline.service.presentation.model.AuditedEntityResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfig.class, uses = AuditMapper.class)
public interface AuditedEntityResponseMapper
    extends EntityToApiMapper<AggregateRoot<? extends EntityId>, AuditedEntityResponse> {

  @Mapping(target = "id", source = "id.value")
  @Override
  AuditedEntityResponse convert(AggregateRoot<? extends EntityId> response);
}
