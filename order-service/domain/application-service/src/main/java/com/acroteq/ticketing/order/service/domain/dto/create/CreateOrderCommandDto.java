package com.acroteq.ticketing.order.service.domain.dto.create;

import com.acroteq.application.dto.DataTransferObject;
import com.google.common.collect.ImmutableList;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Optional;

@Builder(toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode
public class CreateOrderCommandDto implements DataTransferObject {

  @NotNull
  private final Long customerId;
  @NotNull
  private final Long airlineId;
  @NotNull
  private final List<OrderItemDto> items;
  @NotNull
  private final OrderAddressDto address;

  @SuppressWarnings("PublicInnerClass")
  public static class CreateOrderCommandDtoBuilder {

    public CreateOrderCommandDtoBuilder items(@Nullable final List<OrderItemDto> items) {
      this.items = Optional.ofNullable(items)
                           .map(ImmutableList::copyOf)
                           .orElse(ImmutableList.of());
      return this;
    }
  }
}
