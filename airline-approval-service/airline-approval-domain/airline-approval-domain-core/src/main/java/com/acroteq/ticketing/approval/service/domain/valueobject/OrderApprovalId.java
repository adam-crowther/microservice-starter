package com.acroteq.ticketing.approval.service.domain.valueobject;

import com.acroteq.ticketing.domain.valueobject.BaseId;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class OrderApprovalId extends BaseId {

  public static OrderApprovalId of(final Long id) {
    return OrderApprovalId.builder()
                          .value(id)
                          .build();
  }
}
