package com.acroteq.ticketing.order.service.domain.dto.create;

import com.google.common.collect.ImmutableList;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CreateOrderCommandDto {

  @NotNull
  private final Long customerId;
  @NotNull
  private final Long airlineId;
  @NotNull
  private final ImmutableList<OrderItemDto> items;
  @NotNull
  private final OrderAddressDto address;

  @SuppressWarnings("PublicInnerClass")
  public static class CreateOrderCommandDtoBuilder {

    public CreateOrderCommandDtoBuilder items(final List<OrderItemDto> items) {
      this.items = ImmutableList.copyOf(items);
      return this;
    }
  }
}
