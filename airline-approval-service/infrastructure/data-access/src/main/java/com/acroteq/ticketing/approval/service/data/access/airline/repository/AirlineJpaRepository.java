package com.acroteq.ticketing.approval.service.data.access.airline.repository;

import com.acroteq.ticketing.approval.service.data.access.airline.entity.AirlineJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AirlineJpaRepository extends JpaRepository<AirlineJpaEntity, Long> {

  Optional<AirlineJpaEntity> findByCode(String code);
  
  void deleteByCode(String code);
}
