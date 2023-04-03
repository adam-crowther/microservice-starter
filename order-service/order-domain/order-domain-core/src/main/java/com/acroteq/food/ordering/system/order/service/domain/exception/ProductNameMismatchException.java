package com.acroteq.food.ordering.system.order.service.domain.exception;

import com.acroteq.food.ordering.system.domain.exception.DomainException;

public class ProductNameMismatchException extends DomainException {

  private static final String I18N_CODE = "problem.product.name.mismatch";
  private static final String MESSAGE = "The product name '%s' does not match expected '%s'";

  private final String actual;
  private final String expected;

  public ProductNameMismatchException(final String actual, final String expected) {
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
    return new String[]{ actual, expected };
  }
}
