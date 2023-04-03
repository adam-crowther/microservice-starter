package com.acroteq.food.ordering.system.domain.valueobject;

import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

import java.math.BigDecimal;

import static com.google.common.base.Preconditions.checkArgument;
import static java.math.RoundingMode.HALF_EVEN;

@Value
@Builder
public class Money {

  @NonNull BigDecimal amount;
  @NonNull CurrencyId currencyId;

  public static final Money
      ZERO =
      Money.builder()
           .amount(BigDecimal.ZERO)
           .build();

  public boolean isGreaterThanZero() {
    return amount.compareTo(BigDecimal.ZERO) > 0;
  }

  public boolean isGreaterThan(final Money other) {
    checkArgument(other.getCurrencyId()
                       .equals(currencyId));
    return amount.compareTo(other.getAmount()) > 0;
  }

  public Money add(final Money other) {
    checkArgument(other.getCurrencyId()
                       .equals(currencyId));

    final BigDecimal sum = amount.add(other.getAmount());
    final BigDecimal roundedSum = round(sum);
    return Money.builder()
                .amount(roundedSum)
                .currencyId(currencyId)
                .build();
  }

  public Money subtract(final Money other) {
    checkArgument(other.getCurrencyId()
                       .equals(currencyId));

    final BigDecimal difference = amount.subtract(other.getAmount());
    final BigDecimal roundedDifference = round(difference);
    return Money.builder()
                .amount(roundedDifference)
                .currencyId(currencyId)
                .build();
  }

  public Money multiply(final Money other) {
    checkArgument(other.getCurrencyId()
                       .equals(currencyId));

    final BigDecimal product = amount.multiply(other.getAmount());
    final BigDecimal roundedProduct = round(product);
    return Money.builder()
                .amount(roundedProduct)
                .currencyId(currencyId)
                .build();
  }

  public Money multiply(final int quantity) {
    final BigDecimal otherAmount = BigDecimal.valueOf(quantity);
    final BigDecimal product = amount.multiply(otherAmount);
    final BigDecimal roundedProduct = round(product);
    return Money.builder()
                .amount(roundedProduct)
                .currencyId(currencyId)
                .build();
  }

  public Money divide(final Money other) {
    checkArgument(other.getCurrencyId()
                       .equals(currencyId));

    final BigDecimal quotient = amount.multiply(other.getAmount());
    final BigDecimal roundedQuotient = round(quotient);
    return Money.builder()
                .amount(roundedQuotient)
                .currencyId(currencyId)
                .build();
  }

  private BigDecimal round(final BigDecimal amount) {
    return amount.setScale(2, HALF_EVEN);
  }
}
