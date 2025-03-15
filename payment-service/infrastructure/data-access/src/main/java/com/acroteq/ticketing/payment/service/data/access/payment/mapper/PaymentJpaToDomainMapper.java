package com.acroteq.ticketing.payment.service.data.access.payment.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.CurrencyIdMapper;
import com.acroteq.application.mapper.id.CustomerIdMapper;
import com.acroteq.application.mapper.id.OrderIdMapper;
import com.acroteq.application.mapper.id.PaymentIdMapper;
import com.acroteq.infrastructure.mapper.JpaToDomainMapper;
import com.acroteq.ticketing.payment.service.data.access.customer.mapper.CustomerJpaToDomainMapper;
import com.acroteq.ticketing.payment.service.data.access.payment.entity.PaymentJpaEntity;
import com.acroteq.ticketing.payment.service.domain.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfig.class,
        uses = { PaymentIdMapper.class,
                 CurrencyIdMapper.class,
                 OrderIdMapper.class,
                 CustomerIdMapper.class,
                 CustomerJpaToDomainMapper.class })
public interface PaymentJpaToDomainMapper extends JpaToDomainMapper<PaymentJpaEntity, Payment> {

  @Mapping(target = "value.currencyId", source = "valueCurrencyId")
  @Mapping(target = "value.amount", source = "valueAmount")
  @Override
  Payment convertJpaToDomain(PaymentJpaEntity paymentJpaEntity);
}
