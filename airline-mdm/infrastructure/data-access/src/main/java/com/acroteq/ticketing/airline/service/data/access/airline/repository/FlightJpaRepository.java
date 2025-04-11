package com.acroteq.ticketing.airline.service.data.access.airline.repository;

import com.acroteq.ticketing.airline.service.data.access.airline.entity.FlightJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FlightJpaRepository extends JpaRepository<FlightJpaEntity, Long> {

  Optional<FlightJpaEntity> findByCode(String code);

  Optional<Long> findIdByCode(String code);

  void deleteByCode(String code);
}
