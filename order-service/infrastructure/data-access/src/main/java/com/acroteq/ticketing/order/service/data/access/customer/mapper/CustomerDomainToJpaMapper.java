package com.acroteq.ticketing.order.service.data.access.customer.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.CustomerIdMapper;
import com.acroteq.infrastructure.mapper.DomainToJpaMapper;
import com.acroteq.infrastructure.mapper.EventIdDomainToJpaMapper;
import com.acroteq.ticketing.order.service.data.access.customer.entity.CustomerJpaEntity;
import com.acroteq.ticketing.order.service.domain.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapstructConfig.class, uses = { CustomerIdMapper.class, EventIdDomainToJpaMapper.class })
public interface CustomerDomainToJpaMapper extends DomainToJpaMapper<Customer, CustomerJpaEntity> {

  @Override
  CustomerJpaEntity convertDomainToJpa(Customer entity);

  @Mapping(target = "version", source = "entity.version")
  @Mapping(target = "eventId", source = "entity.eventId")
  @Override
  CustomerJpaEntity convertDomainToJpa(Customer entity, @MappingTarget CustomerJpaEntity jpaEntity);
}
