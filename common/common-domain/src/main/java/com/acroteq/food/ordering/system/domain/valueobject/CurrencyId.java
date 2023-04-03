package com.acroteq.food.ordering.system.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

import static java.util.UUID.randomUUID;

@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CurrencyId extends BaseId<UUID> {

    public static CurrencyId of(final UUID id) {
        return CurrencyId.builder().id(id).build();
    }

    public static CurrencyId random() {
    return CurrencyId.of(randomUUID());
    }
}
