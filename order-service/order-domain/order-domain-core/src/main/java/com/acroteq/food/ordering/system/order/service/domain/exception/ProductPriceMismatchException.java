package com.acroteq.food.ordering.system.order.service.domain.exception;

import com.acroteq.food.ordering.system.domain.exception.DomainException;
import com.acroteq.food.ordering.system.domain.valueobject.CashValue;

public class ProductPriceMismatchException extends DomainException {

  private static final String I18N_CODE = "problem.product.price.mismatch";
  private static final String MESSAGE = "The product price '%s' does not match expected '%s'";

  private final CashValue actual;
  private final CashValue expected;

  public ProductPriceMismatchException(final CashValue actual, final CashValue expected) {
    super(String.format(MESSAGE, actual, expected));
    this.actual = actual;
    this.expected = expected;
  }

  @Override
  public String getCode() {
    return I18N_CODE;
  }

  @Override
  public String[] getParameters() {
    return new String[]{ actual.toString(), expected.toString() };
  }
}
