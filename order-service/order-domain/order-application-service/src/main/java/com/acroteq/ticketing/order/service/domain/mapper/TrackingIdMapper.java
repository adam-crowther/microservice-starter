package com.acroteq.ticketing.order.service.domain.mapper;

import com.acroteq.ticketing.order.service.domain.valueobject.TrackingId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(imports = UUID.class)
public interface TrackingIdMapper {

  default UUID convertIdToUuid(final TrackingId trackingId) {
    return trackingId.getValue();
  }

  default String convertIdToString(final TrackingId id) {
    return convertIdToUuid(id).toString();
  }

  @Mapping(target = "value", expression = "java(UUID.fromString(id))")
  TrackingId convertStringToId(String id);

  @Mapping(target = "value", source = "id")
  TrackingId convertLongToId(UUID id);
}
