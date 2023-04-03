package com.acroteq.food.ordering.system.order.service.domain.exception;

import com.acroteq.food.ordering.system.domain.exception.DomainException;
import com.acroteq.food.ordering.system.domain.valueobject.ProductId;

public class ProductNotFoundException extends DomainException {

  public static final String MESSAGE = "Product not found: ";

  public ProductNotFoundException(final ProductId productId) {
    super(MESSAGE + productId);
  }
}
