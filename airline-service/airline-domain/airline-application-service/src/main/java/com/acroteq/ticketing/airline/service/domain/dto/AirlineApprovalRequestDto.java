package com.acroteq.ticketing.airline.service.domain.dto;

import static lombok.AccessLevel.PRIVATE;

import com.acroteq.ticketing.domain.valueobject.OrderStatus;
import com.google.common.collect.ImmutableList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor(access = PRIVATE)
public class AirlineApprovalRequestDto {

  private final Long id;
  private final UUID sagaId;
  private final Long airlineId;
  private final Long orderId;
  private final OrderStatus orderStatus;
  private final ImmutableList<OrderItemDto> items;
  private final Instant createdDateTime;

  @SuppressWarnings("PublicInnerClass")
  public static class AirlineApprovalRequestDtoBuilder {

    public AirlineApprovalRequestDtoBuilder items(final List<OrderItemDto> items) {
      this.items = ImmutableList.copyOf(items);
      return this;
    }
  }
}
