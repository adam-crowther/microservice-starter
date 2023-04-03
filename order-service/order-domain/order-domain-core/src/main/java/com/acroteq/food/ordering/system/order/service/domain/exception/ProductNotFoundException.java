package com.acroteq.food.ordering.system.order.service.domain.exception;

import com.acroteq.food.ordering.system.domain.exception.DomainException;
import com.acroteq.food.ordering.system.domain.valueobject.ProductId;

public class ProductNotFoundException extends DomainException {

  private static final String I18N_CODE = "problem.product.not.found";
  private static final String MESSAGE = "Product not found: ";

  private final ProductId productId;

  public ProductNotFoundException(final ProductId productId) {
    super(MESSAGE + productId);
    this.productId = productId;
  }

  @Override
  public String getCode() {
    return I18N_CODE;
  }

  @Override
  public String[] getParameters() {
    return new String[]{ productId.toString() };
  }
}
