package com.acroteq.food.ordering.system.domain.valueobject;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static java.math.RoundingMode.HALF_EVEN;
import static org.apache.commons.lang3.ObjectUtils.firstNonNull;

@Value
@Builder
public class CashValue {

  @NonNull BigDecimal amount;
  @Nullable CurrencyId currencyId;

  public static final CashValue ZERO = CashValue.builder()
                                                .amount(BigDecimal.ZERO)
                                                .build();

  public boolean isGreaterThanZero() {
    return amount.compareTo(BigDecimal.ZERO) > 0;
  }

  public boolean isGreaterThan(final CashValue other) {
    checkMatchingCurrency(other);

    return amount.compareTo(other.getAmount()) > 0;
  }

  private CurrencyId checkMatchingCurrency(final CashValue other) {
    if (!amount.equals(BigDecimal.ZERO) &&
        !other.getAmount()
              .equals(BigDecimal.ZERO)) {
      checkArgument(Objects.equals(currencyId, other.getCurrencyId()),
                    "The other Money object does not have a matching currency.  this: %s, other: %s",
                    this,
                    other);
    }

    return firstNonNull(currencyId, other.getCurrencyId());
  }

  public CashValue add(final CashValue other) {
    final CurrencyId currencyId = checkMatchingCurrency(other);
    final BigDecimal sum = amount.add(other.getAmount());
    final BigDecimal roundedSum = round(sum);

    return CashValue.builder()
                    .amount(roundedSum)
                    .currencyId(currencyId)
                    .build();
  }

  public CashValue subtract(final CashValue other) {
    final CurrencyId currencyId = checkMatchingCurrency(other);
    final BigDecimal difference = amount.subtract(other.getAmount());
    final BigDecimal roundedDifference = round(difference);
    return CashValue.builder()
                    .amount(roundedDifference)
                    .currencyId(currencyId)
                    .build();
  }

  public CashValue multiply(final CashValue other) {
    final CurrencyId currencyId = checkMatchingCurrency(other);
    final BigDecimal product = amount.multiply(other.getAmount());
    final BigDecimal roundedProduct = round(product);
    return CashValue.builder()
                    .amount(roundedProduct)
                    .currencyId(currencyId)
                    .build();
  }

  public CashValue multiply(final int quantity) {
    final BigDecimal otherAmount = BigDecimal.valueOf(quantity);
    final BigDecimal product = amount.multiply(otherAmount);
    final BigDecimal roundedProduct = round(product);
    return CashValue.builder()
                    .amount(roundedProduct)
                    .currencyId(currencyId)
                    .build();
  }

  public CashValue divide(final CashValue other) {
    final CurrencyId currencyId = checkMatchingCurrency(other);
    final BigDecimal quotient = amount.multiply(other.getAmount());
    final BigDecimal roundedQuotient = round(quotient);
    return CashValue.builder()
                    .amount(roundedQuotient)
                    .currencyId(currencyId)
                    .build();
  }

  private BigDecimal round(final BigDecimal amount) {
    return amount.setScale(2, HALF_EVEN);
  }
}
