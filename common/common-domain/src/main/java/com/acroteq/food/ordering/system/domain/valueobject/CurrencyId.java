package com.acroteq.food.ordering.system.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

import static java.util.UUID.randomUUID;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class CurrencyId extends BaseId<UUID> {

    public static CurrencyId of(@NonNull final UUID id) {
        return CurrencyId.builder().value(id).build();
    }

    public static CurrencyId random() {
    return CurrencyId.of(randomUUID());
    }
}
