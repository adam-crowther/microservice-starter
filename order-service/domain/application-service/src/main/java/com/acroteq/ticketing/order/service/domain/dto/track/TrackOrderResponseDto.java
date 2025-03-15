package com.acroteq.ticketing.order.service.domain.dto.track;

import static com.acroteq.domain.validation.ValidationResult.pass;

import com.acroteq.application.dto.AuditDto;
import com.acroteq.application.dto.DataTransferObject;
import com.acroteq.domain.validation.ValidationResult;
import com.acroteq.domain.valueobject.OrderStatus;
import com.acroteq.ticketing.order.service.domain.dto.create.OrderAddressDto;
import com.acroteq.ticketing.order.service.domain.dto.create.OrderItemDto;
import com.google.common.collect.ImmutableList;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Builder(toBuilder = true)
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TrackOrderResponseDto implements DataTransferObject {

  @NotNull
  private final Long id;
  @NotNull
  private final Long customerId;
  @NotNull
  private final Long airlineId;
  @NotNull
  private final OrderAddressDto streetAddress;
  @NotNull
  private final List<OrderItemDto> items;
  @NotNull
  private final UUID trackingId;
  @NotNull
  private final AuditDto audit;
  @NotNull
  private final OrderStatus orderStatus;

  @Builder.Default
  @NotNull
  private final ValidationResult result = pass();

  @SuppressWarnings("PublicInnerClass")
  public static class TrackOrderResponseDtoBuilder {

    public TrackOrderResponseDtoBuilder items(@Nullable final List<OrderItemDto> items) {
      this.items = Optional.ofNullable(items)
                           .map(ImmutableList::copyOf)
                           .orElse(ImmutableList.of());
      return this;
    }
  }
}
