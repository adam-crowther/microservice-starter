package com.acroteq.food.ordering.system.order.service.domain.test.helper;

import com.acroteq.food.ordering.system.domain.valueobject.CashValue;
import com.acroteq.food.ordering.system.domain.valueobject.CurrencyId;
import com.acroteq.food.ordering.system.domain.valueobject.ProductId;
import com.acroteq.food.ordering.system.order.service.domain.entity.Product;
import org.springframework.boot.test.context.TestComponent;

import java.math.BigDecimal;
import java.util.UUID;

@TestComponent
public class ProductTestDataHelper {

  public static final UUID CURRENCY_ID = UUID.fromString("c784cbff-8500-491b-8d65-07fd6049ce6b");
  public static final UUID PRODUCT_1_ID = UUID.fromString("bc38de38-64f0-4ecf-9bc5-312741ac1fbd");
  public static final String PRODUCT_1_NAME = "PRODUCT 1";
  public static final BigDecimal PRODUCT_1_PRICE = new BigDecimal("10.00");
  public static final Product PRODUCT_1 = Product.builder()
                                                 .id(ProductId.of(PRODUCT_1_ID))
                                                 .name(PRODUCT_1_NAME)
                                                 .price(CashValue.builder()
                                                                 .amount(PRODUCT_1_PRICE)
                                                                 .currencyId(CurrencyId.of(CURRENCY_ID))
                                                                 .build())
                                                 .build();


  public static final UUID PRODUCT_2_ID = UUID.fromString("b72d233b-c2c8-46e6-aa05-19ebef182601");
  public static final String PRODUCT_2_NAME = "PRODUCT 2";
  public static final BigDecimal PRODUCT_2_PRICE = new BigDecimal("20.00");
  public static final Product PRODUCT_2 = Product.builder()
                                                 .id(ProductId.of(PRODUCT_2_ID))
                                                 .name(PRODUCT_2_NAME)
                                                 .price(CashValue.builder()
                                                                 .amount(PRODUCT_2_PRICE)
                                                                 .currencyId(CurrencyId.of(CURRENCY_ID))
                                                                 .build())
                                                 .build();

  public static final UUID PRODUCT_3_ID = UUID.fromString("71323d93-bf94-4105-a218-21aeb65bebf0");
  public static final String PRODUCT_3_NAME = "PRODUCT 3";
  public static final BigDecimal PRODUCT_3_PRICE = new BigDecimal("30.00");
  public static final Product PRODUCT_3 = Product.builder()
                                                 .id(ProductId.of(PRODUCT_3_ID))
                                                 .name(PRODUCT_3_NAME)
                                                 .price(CashValue.builder()
                                                                 .amount(PRODUCT_3_PRICE)
                                                                 .currencyId(CurrencyId.of(CURRENCY_ID))
                                                                 .build())
                                                 .build();
}
