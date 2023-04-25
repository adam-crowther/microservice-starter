package com.acroteq.ticketing.approval.service.domain.dto;

import static lombok.AccessLevel.PRIVATE;

import com.acroteq.ticketing.domain.valueobject.OrderStatus;
import com.google.common.collect.ImmutableList;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor(access = PRIVATE)
public class AirlineApprovalRequestDto {

  @NotNull
  private final UUID sagaId;
  @NotNull
  private final Long airlineId;
  @NotNull
  private final Long orderId;
  @NotNull
  private final OrderStatus orderStatus;
  @NotNull
  private final ImmutableList<OrderItemDto> items;
  @NotNull
  private final Instant createdDateTime;

  @SuppressWarnings("PublicInnerClass")
  public static class AirlineApprovalRequestDtoBuilder {

    public AirlineApprovalRequestDtoBuilder items(@Nullable final List<OrderItemDto> items) {
      this.items = Optional.ofNullable(items)
                           .map(ImmutableList::copyOf)
                           .orElse(ImmutableList.of());
      return this;
    }
  }
}
