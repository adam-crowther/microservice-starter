package com.acroteq.ticketing.order.service.data.access.airline.repository;

import com.acroteq.ticketing.order.service.data.access.airline.entity.AirlineJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirlineJpaRepository extends JpaRepository<AirlineJpaEntity, Long> {

  void deleteByCode(String code);
}
