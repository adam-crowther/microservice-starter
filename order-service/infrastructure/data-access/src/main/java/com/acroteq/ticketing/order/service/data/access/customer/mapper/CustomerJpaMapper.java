package com.acroteq.ticketing.order.service.data.access.customer.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.CustomerIdMapper;
import com.acroteq.infrastructure.mapper.EventIdJpaMapper;
import com.acroteq.infrastructure.mapper.JpaMapper;
import com.acroteq.ticketing.order.service.data.access.customer.entity.CustomerJpaEntity;
import com.acroteq.ticketing.order.service.data.access.customer.repository.CustomerJpaRepository;
import com.acroteq.ticketing.order.service.domain.entity.Customer;
import lombok.Getter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(config = MapstructConfig.class, uses = { CustomerIdMapper.class, EventIdJpaMapper.class })
public abstract class CustomerJpaMapper implements JpaMapper<Customer, CustomerJpaEntity> {

  @Getter
  @Autowired
  private CustomerJpaRepository repository;

  @Override
  public abstract Customer convert(CustomerJpaEntity customer);

  @Override
  public abstract CustomerJpaEntity convertNew(Customer entity);

  @Mapping(target = "version", source = "entity.version")
  @Mapping(target = "eventId", source = "entity.eventId")
  @Override
  public abstract CustomerJpaEntity convertExisting(Customer entity, @MappingTarget CustomerJpaEntity jpaEntity);
}
