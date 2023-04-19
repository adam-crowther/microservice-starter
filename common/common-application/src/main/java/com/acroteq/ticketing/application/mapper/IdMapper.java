package com.acroteq.ticketing.application.mapper;

import com.acroteq.ticketing.domain.valueobject.BaseId;

@SuppressWarnings({ "PMD.AvoidDuplicateLiterals" })
public interface IdMapper<IdT extends BaseId> {

  default Long convertBaseIdToLong(final IdT baseId) {
    return baseId.getValue();
  }

  default String convertBaseIdToString(final IdT id) {
    return convertBaseIdToLong(id).toString();
  }

  IdT convertStringToId(String id);

  IdT convertLongToId(Long id);
}
