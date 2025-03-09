package com.acroteq.domain.valueobject;

import static com.acroteq.domain.valueobject.CurrencyId.NONE;
import static com.google.common.base.Preconditions.checkArgument;
import static java.math.RoundingMode.HALF_EVEN;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Value
@Builder
public class CashValue implements Serializable {


  public static final CashValue ZERO = CashValue.builder()
                                                .amount(BigDecimal.ZERO)
                                                .currencyId(NONE)
                                                .build();
  @NonNull BigDecimal amount;
  @NonNull CurrencyId currencyId;


  public boolean isGreaterThan(final CashValue other) {
    checkMatchingCurrency(other);
    return amount.compareTo(other.getAmount()) > 0;
  }

  public boolean isGreaterThanOrEqualTo(final CashValue other) {
    checkMatchingCurrency(other);
    return amount.compareTo(other.getAmount()) >= 0;
  }

  public boolean isLessThan(final CashValue other) {
    checkMatchingCurrency(other);
    return amount.compareTo(other.getAmount()) < 0;
  }

  public boolean isLessThanOrEqualTo(final CashValue other) {
    checkMatchingCurrency(other);
    return amount.compareTo(other.getAmount()) <= 0;
  }

  private CurrencyId checkMatchingCurrency(final CashValue other) {
    if (amount.compareTo(BigDecimal.ZERO) != 0
        && other.getAmount()
                .compareTo(BigDecimal.ZERO) != 0) {
      checkArgument(Objects.equals(currencyId, other.getCurrencyId()),
                    "The other Money object does not have a matching currency. this: %s, other: %s",
                    this,
                    other);
    }

    return Stream.of(this, other)
                 .filter(this::isAmountNotZero)
                 .map(CashValue::getCurrencyId)
                 .findFirst()
                 .orElseGet(getNotNoneCurrencyId(this, other));
  }

  private boolean isAmountNotZero(final CashValue value) {
    return value.getAmount()
                .compareTo(BigDecimal.ZERO) != 0;
  }

  private Supplier<CurrencyId> getNotNoneCurrencyId(final CashValue left, final CashValue right) {
    return () -> Stream.of(left, right)
                       .map(CashValue::getCurrencyId)
                       .filter(CurrencyId::isNotNone)
                       .findFirst()
                       .orElse(NONE);
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

  public CashValue multiply(final BigDecimal quantity) {
    final BigDecimal product = amount.multiply(quantity);
    final BigDecimal roundedProduct = round(product);
    return CashValue.builder()
                    .amount(roundedProduct)
                    .currencyId(currencyId)
                    .build();
  }

  public CashValue divide(final BigDecimal quotient) {
    final BigDecimal roundedQuotient = amount.divide(quotient, 2, HALF_EVEN);
    return CashValue.builder()
                    .amount(roundedQuotient)
                    .currencyId(currencyId)
                    .build();
  }

  public CashValue absolute() {
    final BigDecimal absoluteAmount = amount.abs();
    return CashValue.builder()
                    .amount(absoluteAmount)
                    .currencyId(currencyId)
                    .build();
  }

  private BigDecimal round(final BigDecimal amount) {
    return amount.setScale(2, HALF_EVEN);
  }

  @Override
  public int hashCode() {
    return Objects.hash(amount.doubleValue(), currencyId);
  }

  @Override
  @SuppressWarnings("PMD.OnlyOneReturn")
  public boolean equals(final Object object) {
    return object == this //
           || object instanceof final CashValue other   //
              && amount.compareTo(other.getAmount()) == 0 //
              && currencyId.equals(other.getCurrencyId()); //
  }
}
