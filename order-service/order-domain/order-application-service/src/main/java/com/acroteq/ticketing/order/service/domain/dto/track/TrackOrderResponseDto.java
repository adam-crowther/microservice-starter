package com.acroteq.ticketing.order.service.domain.dto.track;

import static com.acroteq.ticketing.domain.validation.ValidationResult.pass;

import com.acroteq.ticketing.domain.validation.ValidationResult;
import com.acroteq.ticketing.domain.valueobject.OrderStatus;
import com.acroteq.ticketing.order.service.domain.dto.create.OrderAddressDto;
import com.acroteq.ticketing.order.service.domain.dto.create.OrderItemDto;
import com.google.common.collect.ImmutableList;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
public class TrackOrderResponseDto {

  @NotNull
  private final Long customerId;
  @NotNull
  private final Long airlineId;
  @NotNull
  private final OrderAddressDto streetAddress;
  @NotNull
  private final ImmutableList<OrderItemDto> items;
  @NotNull
  private UUID trackingId;
  @NotNull
  private OrderStatus orderStatus;

  @Builder.Default
  @NotNull
  private final ValidationResult result = pass();

  @SuppressWarnings("PublicInnerClass")
  public static class TrackOrderResponseDtoBuilder {

    public TrackOrderResponseDtoBuilder items(final List<OrderItemDto> items) {
      this.items = ImmutableList.copyOf(items);
      return this;
    }
  }
}
