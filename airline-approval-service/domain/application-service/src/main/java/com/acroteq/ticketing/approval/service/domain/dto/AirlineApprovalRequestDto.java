package com.acroteq.ticketing.approval.service.domain.dto;

import com.acroteq.application.dto.SagaDto;
import com.acroteq.domain.valueobject.OrderStatus;
import com.google.common.collect.ImmutableList;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Optional;

@SuperBuilder(toBuilder = true)
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class AirlineApprovalRequestDto extends SagaDto {

  @NotNull
  private final Long airlineId;
  @NotNull
  private final Long orderId;
  @NotNull
  private final Long orderVersion;
  @NotNull
  private final OrderStatus orderStatus;
  @NotNull
  private final List<OrderItemDto> items;

  public Long getId() {
    return orderId;
  }

  @SuppressWarnings("PublicInnerClass")
  public abstract static class AirlineApprovalRequestDtoBuilder<C extends AirlineApprovalRequestDto,
      B extends AirlineApprovalRequestDtoBuilder<C, B>>
      extends SagaDtoBuilder<C, B> {

    public AirlineApprovalRequestDtoBuilder<C, B> items(@Nullable final List<OrderItemDto> items) {
      this.items = Optional.ofNullable(items)
                           .map(ImmutableList::copyOf)
                           .orElse(ImmutableList.of());
      return this.self();
    }
  }
}
