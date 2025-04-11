package com.acroteq.ticketing.airline.service.presentation.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.common.application.mapper.EntityToApiMapper;
import com.acroteq.domain.entity.PrimaryEntity;
import com.acroteq.domain.valueobject.EntityId;
import com.acroteq.ticketing.airline.service.presentation.model.EntityResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfig.class, uses = AuditMapper.class)
public interface EntityResponseMapper extends EntityToApiMapper<PrimaryEntity<? extends EntityId>, EntityResponse> {

  @Mapping(target = "id", source = "id.value")
  @Override
  EntityResponse convert(PrimaryEntity<? extends EntityId> response);
}
