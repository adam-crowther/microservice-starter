package com.acroteq.food.ordering.system.order.service.domain.valueobject;

import com.acroteq.food.ordering.system.domain.valueobject.BaseId;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OrderItemId extends BaseId<Long> {

  public static OrderItemId of(final Long id) {
    return OrderItemId.builder().id(id).build();
  }
}
