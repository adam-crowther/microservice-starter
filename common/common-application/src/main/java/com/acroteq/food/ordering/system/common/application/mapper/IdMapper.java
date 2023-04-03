package com.acroteq.food.ordering.system.common.application.mapper;

import com.acroteq.food.ordering.system.domain.valueobject.BaseId;
import com.acroteq.food.ordering.system.domain.valueobject.CurrencyId;
import com.acroteq.food.ordering.system.domain.valueobject.CustomerId;
import com.acroteq.food.ordering.system.domain.valueobject.OrderId;
import com.acroteq.food.ordering.system.domain.valueobject.ProductId;
import com.acroteq.food.ordering.system.domain.valueobject.RestaurantId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper
public interface IdMapper {

  default <T> T convertBaseIdToUuid(final BaseId<T> baseId) {
    return baseId.getValue();
  }

  default <T> String convertBaseIdToString(final BaseId<T> currencyId) {
    return convertBaseIdToUuid(currencyId).toString();
  }

  @Mapping(target = "value", source = "uuid")
  CurrencyId convertUuidToCurrencyId(UUID uuid);

  @Mapping(target = "value", source = "uuid")
  CustomerId convertUuidToCustomerId(UUID uuid);

  @Mapping(target = "value", source = "uuid")
  OrderId convertUuidToOrderId(UUID uuid);

  @Mapping(target = "value", source = "uuid")
  ProductId convertUuidToProductId(UUID uuid);

  @Mapping(target = "value", source = "uuid")
  RestaurantId convertUuidToRestaurantId(UUID uuid);
}
